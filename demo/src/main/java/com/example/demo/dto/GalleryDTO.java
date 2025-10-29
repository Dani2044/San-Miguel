package com.example.demo.dto;
import java.util.List;
public record GalleryDTO(
    Long id,
    List<String> photos
) {}
