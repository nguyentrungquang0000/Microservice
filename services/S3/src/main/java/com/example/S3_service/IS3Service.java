package com.example.S3_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IS3Service{
    FileResponse uploadFile(String key, MultipartFile file);
    ResponseEntity<String> deleteFile(String key);
    ResponseEntity<String> deleteFolder(String folderPrefix);
}
