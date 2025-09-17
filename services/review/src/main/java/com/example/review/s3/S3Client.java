package com.example.review.s3;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "s3-service", url = "http://localhost:8090/api/v1/s3-service")
public interface S3Client {
    @PostMapping(value = "/{key}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FileResponse uploadFile(@RequestPart("file") MultipartFile file, @PathVariable String key);

    @DeleteMapping("/delete-all/{folderPrefix}")
    ResponseEntity<String> deleteAll(@PathVariable String folderPrefix);
}
