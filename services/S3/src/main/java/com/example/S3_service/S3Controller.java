package com.example.S3_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/s3-service")
public class S3Controller {
    @Autowired
    IS3Service s3Service;

    @PostMapping("/{key}")
    public FileResponse uploadFile(@RequestPart("file") MultipartFile file,
                                   @PathVariable String key) {
        return s3Service.uploadFile(key, file);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<String> deleteFile(@PathVariable String key) {
        return s3Service.deleteFile(key);
    }

    @DeleteMapping("/delete-all/{folderPrefix}")
    public ResponseEntity<String> deleteAll(@PathVariable String folderPrefix) {
        return s3Service.deleteFolder(folderPrefix);
    }
}
