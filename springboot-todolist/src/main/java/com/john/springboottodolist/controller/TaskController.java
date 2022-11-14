package com.john.springboottodolist.controller;

import com.john.springboottodolist.entity.Task;
import com.john.springboottodolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String listTasks(Model theModel){

        // 從資料庫取出資料
        List<Task> taskList = taskService.findAll();

        // 添加到 spring model
        theModel.addAttribute("tasks",taskList);

        return "tasks/list-tasks";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){

        Task task = new Task();

        theModel.addAttribute("task",task);

        return "tasks/task-form";
    }

    @PostMapping("/save")
    // 這裡的 @ModelAttribute的 task 要跟 task-form 裡的  th:object="${task} 這裡一樣
    public String saveTask(@ModelAttribute("task") Task theTask){

        // save the task
        taskService.save(theTask);

        return "redirect:/tasks/list";
    }
}
