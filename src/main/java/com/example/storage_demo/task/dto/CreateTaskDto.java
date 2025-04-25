package com.example.storage_demo.task.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateTaskDto {
    @NotBlank
    private String name;

    public CreateTaskDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
