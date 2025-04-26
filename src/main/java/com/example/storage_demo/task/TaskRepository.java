package com.example.storage_demo.task;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // for eager loading of attachments
    @EntityGraph(attributePaths = { "entityAttachments", "entityAttachments.attachment" })
    Optional<Task> findWithAttachmentsById(Long id);

    @EntityGraph(attributePaths = { "entityAttachments", "entityAttachments.attachment" })
    @Query("SELECT t FROM Task t")
    List<Task> findAllWithAttachments();
}
