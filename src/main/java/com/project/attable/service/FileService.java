package com.project.attable.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    String uploadFile(MultipartFile file) throws IOException;

    List<String> uploadFiles(MultipartFile[] file) throws IOException;

    String deleteFile(String fileDestination) throws IOException;

    String getFile(String fileName) throws IOException;
}
