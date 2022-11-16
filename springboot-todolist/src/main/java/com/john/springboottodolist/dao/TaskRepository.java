package com.john.springboottodolist.dao;

import com.john.springboottodolist.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    @Query("SELECT p FROM Task p WHERE p.taskName LIKE %?1%")
    Page<Task> findAll(String keyword, Pageable pageable);

    List<Task> findByTaskNameContainsAllIgnoreCase(String taskName);

    Page<Task> findAll(Pageable pageable);
}
