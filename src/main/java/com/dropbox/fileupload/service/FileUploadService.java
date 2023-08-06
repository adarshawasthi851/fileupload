package com.dropbox.fileupload.service;

import com.dropbox.fileupload.model.FileUpload;
import com.dropbox.fileupload.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FileUploadService {

    @Autowired
    FileUploadRepository fileUploadRepository;

    String homeDir = System.getProperty("user.home");
    String uploadDir = homeDir + "/uploads/";

    public ResponseEntity<String> uploadFile(MultipartFile file) {
        try {
            FileUpload newFile = new FileUpload();
            newFile.setFileName(file.getOriginalFilename());
            newFile.setCreatedTime(LocalDateTime.now());
            fileUploadRepository.save(newFile);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload the file: " + e.getMessage());
        }
    }
    public void storeFile(String filename, MultipartFile file) throws IOException {
        // Save the file to the specified directory
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));

        // Create a unique subdirectory name based on the formattedDateTime
        String subDirectoryName = uploadPath.resolve(formattedDateTime).toString();

        // Create the subdirectory using the unique name
        Path subDirectory = Paths.get(subDirectoryName);
        if (!Files.exists(subDirectory)) {
            Files.createDirectory(subDirectory);
        }

        // Create a separate file path within the subdirectory for the uploaded file
        Path filePath = subDirectory.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        FileUpload newFile = new FileUpload();
        newFile.setFileName(filename);
        newFile.setUrl(filename);
        newFile.setCreatedTime(LocalDateTime.now());
        fileUploadRepository.save(newFile);
    }




    public List<FileUpload> findAllFiles() {
        return fileUploadRepository.findAll();
    }
}
