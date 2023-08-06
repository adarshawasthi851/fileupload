package com.dropbox.fileupload.repository;


import com.dropbox.fileupload.model.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<FileUpload, Integer> {
}
