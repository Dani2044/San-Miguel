package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GalleryItemDTO;
import com.example.demo.service.GalleryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping
    public List<GalleryItemDTO> getAll() {
        return galleryService.getAll();
    }

    @PostMapping
    public ResponseEntity<GalleryItemDTO> create(@RequestBody GalleryItemDTO dto) {
        return ResponseEntity.ok(galleryService.create(dto));
    }
}
