package com.john.springboottodolist.service;

import com.john.springboottodolist.entity.Task;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {

    Page<Task> findAll(int pageNumber, String sortField, String sort,String keyword);

    Task findById(int id);

    void save(Task theTask);

    void deleteById(int id);


}
