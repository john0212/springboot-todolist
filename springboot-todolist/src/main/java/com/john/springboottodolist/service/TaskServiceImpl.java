package com.john.springboottodolist.service;

import com.john.springboottodolist.dao.TaskRepository;
import com.john.springboottodolist.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository theTaskRepository) {
        taskRepository = theTaskRepository;
    }


    public Page<Task> findAll(int pageNumber, String sortField, String sortDir,String keyword) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);

        if (keyword != null){
            return taskRepository.findAll(keyword,pageable);
        }

        return taskRepository.findAll(pageable);
    }

    @Override
    public Task findById(int id) {

        Optional<Task> result = taskRepository.findById(id);

        Task theTask = null;

        if (result.isPresent()) {
            theTask = result.get();
        } else {
            throw new RuntimeException("Did not found task id - " + id);
        }

        return theTask;
    }

    @Override
    public void save(Task theTask) {
        taskRepository.save(theTask);
    }

    @Override
    public void deleteById(int id) {
        taskRepository.deleteById(id);
    }



}
