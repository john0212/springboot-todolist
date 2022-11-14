package com.john.springboottodolist.service;

import com.john.springboottodolist.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

}
