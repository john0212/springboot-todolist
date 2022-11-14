package com.john.springboottodolist.service;

import com.john.springboottodolist.dao.TaskRepository;
import com.john.springboottodolist.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository theTaskRepository) {
        taskRepository = theTaskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAllByOrderByTaskNameAsc();
    }


}
