package com.project.attable.security.controller;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.project.attable.service.FileService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ImageController {
    @Autowired
    FileService fileService;

    @Value("${physicalImageLocation}")
    String physicalImageLocation;
    //Save the uploaded file to this folder

    @Value("${imageServer}")
    String imageServer;
    private String UPLOADED_FOLDER;

    @PostConstruct
    public void init() {
        String prefix;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) {
            prefix = "C:";
        } else if (os.indexOf("mac") >= 0) {
            prefix = System.getProperty("user.home");
        } else {
            prefix = "";
        }

        UPLOADED_FOLDER = prefix + physicalImageLocation;
    }

    @GetMapping(
            value = "/images/{fileName:.+}",
            produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody
    byte[] getImage(@PathVariable("fileName") String fileName) throws IOException {
        File file = new File(UPLOADED_FOLDER + fileName);
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);
    }


    @PostMapping("/upload") // //new annotation since 4.3
    public ResponseEntity<?> singleFileUpload(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            String destination = fileService.uploadFile(file);
            return ResponseEntity.ok(destination);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping("/uploadMultiple") // //new annotation since 4.3
    public ResponseEntity<?> mutipleFileUpload(@RequestParam("files") MultipartFile[] files) {

        if (files.length == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            List<String> destinations = fileService.uploadFiles(files);
            return ResponseEntity.ok(destinations);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
    
    @PostMapping("/delete")
    public ResponseEntity<?> singleFileDelete(@RequestParam("image") String fileName) {
        try {
            String destination = fileService.deleteFile(UPLOADED_FOLDER + fileName);
            return ResponseEntity.ok(destination);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
