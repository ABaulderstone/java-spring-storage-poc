package com.example.storage_demo.task;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.storage_demo.attachment.AttachmentService;
import com.example.storage_demo.attachment.storage.KeyDetails;
import com.example.storage_demo.task.dto.CreateTaskDto;

import jakarta.transaction.Transactional;

@Service
public class TaskService {
    private final TaskRepository repo;
    private final AttachmentService attachmentService;

    public TaskService(TaskRepository repo, AttachmentService attachmentService) {
        this.repo = repo;
        this.attachmentService = attachmentService;
    }

    @Transactional
    public Task create(CreateTaskDto data) throws IOException {

        Task newTask = new Task();
        newTask.setName(data.getName());
        this.repo.save(newTask);
        MultipartFile file = data.getAttachedFile();

        if (file != null && !file.isEmpty()) {
            try {
                InputStream inputStream = file.getInputStream();
                String contentType = file.getContentType();
                String fileName = file.getOriginalFilename();
                KeyDetails details = newTask.generateKeyDetails(fileName);
                this.attachmentService.createAttachment(inputStream, contentType, details);
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return newTask;
    }

}
