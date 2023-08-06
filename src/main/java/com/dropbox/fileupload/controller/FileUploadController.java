package com.dropbox.fileupload.controller;
import com.dropbox.fileupload.model.FileUpload;
import com.dropbox.fileupload.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/file_upload")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/v1")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file ) {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss")) + file.getOriginalFilename();
        try {
            fileUploadService.storeFile(formattedDateTime, file);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload the file: " + e.getMessage());
        }
    }



    @GetMapping("/get")
    public List<FileUpload> getAllFiles() {
        return fileUploadService.findAllFiles();
    }

}


