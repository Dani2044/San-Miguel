package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    private Path rootLocation() {
        return Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        return uploadImageToFileSystem(file);
    }

    @Override
    public byte[] downloadImage(String fileName) throws IOException {
        Path file = rootLocation().resolve(fileName);
        return Files.readAllBytes(file);
    }

    @Override
    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new IOException("File is empty");
        String original = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = "";
        int i = original.lastIndexOf('.');
        if (i > 0) ext = original.substring(i);
        String filename = UUID.randomUUID().toString() + ext;

        Path target = rootLocation();
        Files.createDirectories(target);
        Path destination = target.resolve(filename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        // Return only the filename (without /uploads/ prefix)
        // The frontend will construct the full URL using getUploadUrl()
        return filename;
    }

    @Override
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        return downloadImage(fileName);
    }
}
