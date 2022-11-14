package com.john.springboottodolist.dao;

import com.john.springboottodolist.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findAllByOrderByTaskNameAsc();
}
