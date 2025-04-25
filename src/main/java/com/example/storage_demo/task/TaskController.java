package com.example.storage_demo.task;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @GetMapping()
    public String index() {
        return "Get all tasks";
    }

}
