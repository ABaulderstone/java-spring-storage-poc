package com.example.storage_demo.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.storage_demo.attachment.Attachment;
import com.example.storage_demo.attachment.AttachmentService;
import com.example.storage_demo.attachment.dto.AttachmentDto;
import com.example.storage_demo.attachment.storage.KeyDetails;
import com.example.storage_demo.task.dto.CreateTaskDto;
import com.example.storage_demo.task.dto.TaskDto;

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

    // TODO: switch to model mapper
    public TaskDto mapTaskDto(Task task, List<AttachmentDto> attachmentDtos) {
        TaskDto dto = new TaskDto();
        dto.setName(task.getName());
        dto.setId(task.getId());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setAttachmentDtos(attachmentDtos);
        return dto;

    }

    public List<TaskDto> getAll() {
        List<Task> tasks = this.repo.findAllWithAttachments();
        // get a list of all attachmentIds so we can batch lookup
        List<Long> attachmentIds = tasks.stream().flatMap(task -> task.getEntityAttachments().stream())
                .map(ea -> ea.getAttachment().getId()).toList();
        // fetch them all at once, think SQL SELECT IN
        List<Attachment> attachments = this.attachmentService.getAttachmentsByIdList(attachmentIds);
        // map attachments to their Ids think like a JS object {1: {...atachment}, 2:
        // {...attachment}}
        Map<Long, Attachment> attachmentMap = attachments.stream()
                .collect(Collectors.toMap(Attachment::getId, Function.identity()));

        List<TaskDto> taskDtos = tasks.stream().map(task -> {
            // TODO: replace with model mapper
            List<AttachmentDto> attachmentDtos = task.getEntityAttachments().stream()
                    .map(ea -> attachmentMap.get(ea.getAttachment().getId()))
                    .map(attachment -> this.attachmentService.mapAttachmentToDto(attachment)).toList();
            return mapTaskDto(task, attachmentDtos);
        }).toList();

        return taskDtos;

    }

}
