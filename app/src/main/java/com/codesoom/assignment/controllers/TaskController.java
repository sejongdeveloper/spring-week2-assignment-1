// TODO
// 4. Update - PUT/PATCH /tasks/{id}
// 5. DELETE /tasks/{id}

package com.codesoom.assignment.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codesoom.assignment.model.Task;
import com.codesoom.assignment.service.TaskService;
import com.codesoom.assignment.service.TaskServiceImpl;

@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {

    TaskService taskService = new TaskServiceImpl();

    @GetMapping
    public List<Task> list() {
        return taskService.getTaskListService();
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task) {
        if (task.getTitle().isBlank()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Task createdTask = this.taskService.createTaskService(task);

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable("id") Long id) {
        Task task = taskService.getTaskService(id);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> modifyTask(@PathVariable("id") Long id, @RequestBody Task source) {
        Task ret = taskService.getTaskService(id);
        if (ret == null || source.getTitle().isBlank()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String title = source.getTitle();
        Task modifiedTask = taskService.modifyTaskService(id, title);
        return new ResponseEntity<>(modifiedTask, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") Long id) {
        Task task = taskService.getTaskService(id);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        taskService.deleteTaskService(task);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
