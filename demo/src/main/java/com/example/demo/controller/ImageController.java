package com.example.demo.controller;

import com.example.demo.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final StorageService service;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) throws IOException {
        byte[] imageData = service.downloadImage(fileName);
        Path p = Paths.get(uploadDir).resolve(fileName).toAbsolutePath();
        String contentType = null;
        try {
            contentType = Files.probeContentType(p);
        } catch (IOException e) {
            // ignore, fallback below
        }
        MediaType mediaType = (contentType != null) ? MediaType.parseMediaType(contentType) : MediaType.APPLICATION_OCTET_STREAM;
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType)
                .body(imageData);
    }

    @PostMapping("/fileSystem")
    public ResponseEntity<String> uploadImageToFileSystem(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = service.uploadImageToFileSystem(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<byte[]> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = service.downloadImageFromFileSystem(fileName);
        Path p = Paths.get(uploadDir).resolve(fileName).toAbsolutePath();
        String contentType = null;
        try {
            contentType = Files.probeContentType(p);
        } catch (IOException e) {
            // ignore
        }
        MediaType mediaType = (contentType != null) ? MediaType.parseMediaType(contentType) : MediaType.APPLICATION_OCTET_STREAM;
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType)
                .body(imageData);
    }

}

