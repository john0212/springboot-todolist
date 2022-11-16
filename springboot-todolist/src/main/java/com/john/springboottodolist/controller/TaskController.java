package com.john.springboottodolist.controller;

import com.john.springboottodolist.entity.Task;
import com.john.springboottodolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService theTaskService) {
        taskService = theTaskService;
    }


    // 顯示全部
    @GetMapping("/list")
    public String listTasks(Model theModel) {
        String keyword = null;
        return listByPage(theModel, 1,"taskName","asc",keyword);

    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model theModel,
                             @PathVariable("pageNumber") int currentPage,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword) {

        // 從資料庫取出資料
        Page<Task> page = taskService.findAll(currentPage,sortField,sortDir,keyword);

        long totalItems = page.getTotalElements();

        int totalPages = page.getTotalPages();

        List<Task> taskList = page.getContent();

        // 添加到 spring model
        theModel.addAttribute("currentPage", currentPage);
        theModel.addAttribute("totalItems", totalItems);
        theModel.addAttribute("totalPages", totalPages);
        theModel.addAttribute("tasks", taskList);
        theModel.addAttribute("sortField", sortField);
        theModel.addAttribute("sortDir", sortDir);
        theModel.addAttribute("keyword", keyword);

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        theModel.addAttribute("reverseSortDir", reverseSortDir);

        return "tasks/list-tasks";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        Task task = new Task();

        theModel.addAttribute("task", task);

        return "tasks/task-form";
    }

    @PostMapping("/save")
    // 這裡的 @ModelAttribute的 task 要跟 task-form 裡的  th:object="${task} 這裡一樣
    public String saveTask(@ModelAttribute("task") Task theTask) {

        // save the task
        taskService.save(theTask);

        return "redirect:/tasks/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("taskId") int theId,
                                    Model theModel) {
        // 從 service 取得 task
        Task theTask = taskService.findById(theId);

        theModel.addAttribute("task", theTask);

        return "tasks/task-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("taskId") int theId) {

        // 刪除
        taskService.deleteById(theId);

        return "redirect:/tasks/list";
    }

}
