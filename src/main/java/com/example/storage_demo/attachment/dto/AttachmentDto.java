package com.example.storage_demo.attachment.dto;

public class AttachmentDto {
    private Long id;
    private String originalFileName;
    private String presignedUrl;

    public AttachmentDto() {
    }

    public Long getId() {
        return id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getPresignedUrl() {
        return presignedUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public void setPresignedUrl(String presignedUrl) {
        this.presignedUrl = presignedUrl;
    }

}
