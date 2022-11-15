package com.john.springboottodolist.service;

import com.john.springboottodolist.dao.TaskRepository;
import com.john.springboottodolist.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<Task> findAll() {
        return taskRepository.findAllByOrderByTaskNameAsc();
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

    @Override
    public List<Task> searchBy(String theName) {

        List<Task> result = null;

        if (theName != null && (theName.trim().length() > 0)) {
            result = taskRepository.findByTaskNameContainsAllIgnoreCase(theName);
        } else {
            result = findAll();
        }

        return result;
    }


}
