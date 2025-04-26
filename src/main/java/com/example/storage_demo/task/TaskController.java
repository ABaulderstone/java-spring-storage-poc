package com.example.storage_demo.task;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.storage_demo.task.dto.CreateTaskDto;
import com.example.storage_demo.task.dto.TaskDto;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public ResponseEntity<List<TaskDto>> index() {
        List<TaskDto> tasks = this.taskService.getAll();
        return ResponseEntity.status(200).body(tasks);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Task> create(@ModelAttribute CreateTaskDto data) throws IOException {
        Task newTask = this.taskService.create(data);
        return ResponseEntity.status(201).body(newTask);

    }

}
