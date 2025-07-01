package com.project.attable.service;


import static org.imgscalr.Scalr.resize;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile({"prod"})
public class FileServiceLocalImpl implements FileService {
    @Value("${physicalImageLocation}")
    String physicalImageLocation;
    //Save the uploaded file to this folder

    @Value("${imageServer}")
    String imageServer;
    @Value("${linux}")
    String isLinux;

    @PostConstruct
    public void init() {

        String prefix;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) {
            prefix = "C:";
        } else {
            prefix = System.getProperty("user.home");
        }
        log.debug("now run on {}", isLinux);
        boolean requiredPrefix = !isLinux.equals("true");
        log.debug("required prefix = {}", requiredPrefix);
        if (requiredPrefix) {
            UPLOADED_FOLDER = prefix + physicalImageLocation;
        } else {
            UPLOADED_FOLDER = physicalImageLocation;
        }
    }

    private String UPLOADED_FOLDER;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        // Get the file and save it somewhere

        byte[] bytes = file.getBytes();
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String newFilename = timeStamp + "-" + file.getOriginalFilename();
        Path path = Paths.get(UPLOADED_FOLDER + newFilename);
        Files.createDirectories(Paths.get(UPLOADED_FOLDER));
        Files.write(path, bytes);
        log.debug("save file " + UPLOADED_FOLDER + newFilename);
        try {
            File resizedFile = new File(newFilename);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(file.getBytes()));

            String extension = "";

            int i = newFilename.lastIndexOf('.');
            int p = Math.max(newFilename.lastIndexOf('/'), newFilename.lastIndexOf('\\'));

            if (i > p) {
                extension = newFilename.substring(i + 1);
            }

            if (file.getSize() >= 10 && extension.toUpperCase().equals("JPG")) {
                ImageIO.write(resize(image, Scalr.Method.ULTRA_QUALITY, 720, Scalr.THRESHOLD_QUALITY_BALANCED), "JPG", resizedFile);
                byte[] data = Files.readAllBytes(resizedFile.toPath());
                Files.write(path, data);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return imageServer + newFilename;
    }

    @Override
    public List<String> uploadFiles(MultipartFile[] files) throws IOException {
        List<String> output = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            String filename = uploadFile(multipartFile);
            output.add(filename);
        }
        return output;
    }

    @Override
    public String deleteFile(String fileDestination) throws IOException {
        String returnValue = "";
        File waitForDeleteFile = new File(fileDestination);
        boolean returnResult = waitForDeleteFile.delete();
        if (returnResult) {
            returnValue = waitForDeleteFile.getName();
        }
        return returnValue;
    }

    @Override
    public String getFile(String fileName) throws IOException {
//        File file = new File(imageServer + fileName);
//        if (!file.exists()) {
//            return null;
//        }
        return imageServer + fileName;
    }

}
