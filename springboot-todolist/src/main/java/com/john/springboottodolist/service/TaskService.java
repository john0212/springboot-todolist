package com.john.springboottodolist.service;

import com.john.springboottodolist.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    Task findById(int id);

    void save(Task theTask);

    void deleteById(int id);

}
