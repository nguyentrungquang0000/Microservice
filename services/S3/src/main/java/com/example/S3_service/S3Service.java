package com.example.S3_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.UUID;

@Service
public class S3Service implements IS3Service {
    @Value("${aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    @Autowired
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public FileResponse uploadFile(String key, MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String extension = "";

            assert originalFileName != null;
            int dotIndex = originalFileName.lastIndexOf(".");
            if (dotIndex > 0) {
                extension = originalFileName.substring(dotIndex);
            }
            String randomFileName = UUID.randomUUID() + extension;
            key = key.replace("*", "/");
            String fileName = key + "/" + randomFileName;
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return FileResponse.builder()
                    .key(fileName)
                    .fileUrl("https://" + bucket + ".s3.amazonaws.com/" + fileName)
                    .type(file.getContentType())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteFile(String key) {
        try{
            key = key.replace("*", "/");
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
            return ResponseEntity.ok("Successfully deleted file " + key);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete False:" + e.getMessage());
        }

    }

    @Override
    public ResponseEntity<String> deleteFolder(String folderPrefix) {
        try {
            folderPrefix = folderPrefix.replace("*", "/") + "/";
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(bucket)
                    .prefix(folderPrefix)
                    .build();

            ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);
            List<S3Object> objectsToDelete = listResponse.contents();

            List<ObjectIdentifier> toDelete = objectsToDelete.stream()
                    .map(obj -> ObjectIdentifier.builder().key(obj.key()).build())
                    .toList();

            if (!toDelete.isEmpty()) {
                DeleteObjectsRequest deleteRequest = DeleteObjectsRequest.builder()
                        .bucket(bucket)
                        .delete(Delete.builder().objects(toDelete).build())
                        .build();

                s3Client.deleteObjects(deleteRequest);
            }
            return ResponseEntity.ok("Successfully deleted " + folderPrefix);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete False:" + e.getMessage());
        }
    }
}
