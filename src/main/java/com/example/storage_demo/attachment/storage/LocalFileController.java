package com.example.storage_demo.attachment.storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/files")
@Profile({ "dev", "test" })
public class LocalFileController {
    private final Path storageLocation;

    public LocalFileController(String storageDir) {
        this.storageLocation = Paths.get(storageDir).toAbsolutePath().normalize();
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<?> serveFile(@PathVariable String filename) {
        try {
            Path filePath = storageLocation.resolve(filename).normalize();
            if (!filePath.toAbsolutePath().startsWith(storageLocation)) {
                return ResponseEntity.badRequest().build();
            }

            UrlResource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

}
