package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String uploadImage(MultipartFile file) throws IOException;
    byte[] downloadImage(String fileName) throws IOException;

    String uploadImageToFileSystem(MultipartFile file) throws IOException;
    byte[] downloadImageFromFileSystem(String fileName) throws IOException;
}
