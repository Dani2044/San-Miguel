package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.GalleryItemDTO;

public interface GalleryService {
    List<GalleryItemDTO> getAll();
    GalleryItemDTO create(GalleryItemDTO dto);
}
