package com.example.demo.controller;

import com.example.demo.dto.GalleryDTO;
import com.example.demo.mapper.GalleryMapper;
import com.example.demo.model.Gallery;
import com.example.demo.repository.GalleryRepository;
import com.example.demo.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryRepository galleryRepository;
    private final StorageService storageService;
    
    @GetMapping("/gallery")
    public ResponseEntity<GalleryDTO> getMainGallery() {
        List<Gallery> all = galleryRepository.findAll();
        if (all.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(GalleryMapper.toDTO(all.get(0)));
    }

    @GetMapping
    public ResponseEntity<List<GalleryDTO>> listAll() {
        List<Gallery> all = galleryRepository.findAll();
        List<GalleryDTO> dtos = new ArrayList<>();
        for (Gallery g : all) dtos.add(GalleryMapper.toDTO(g));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalleryDTO> getById(@PathVariable Long id) {
        Optional<Gallery> opt = galleryRepository.findById(id);
        return opt.map(g -> ResponseEntity.ok(GalleryMapper.toDTO(g)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<GalleryDTO> createEmptyGallery() {
        Gallery g = new Gallery();
        g.setPhotos(new ArrayList<>());
        Gallery saved = galleryRepository.save(g);
        return ResponseEntity.status(HttpStatus.CREATED).body(GalleryMapper.toDTO(saved));
    }

    /**
     * Upload one or more images and add the resulting URLs to the gallery's photo list.
     * Uses the configured StorageService (FileSystemStorageService by default).
     * Returns the updated GalleryDTO.
     */
    @PostMapping("/{id}/photos")
    public ResponseEntity<GalleryDTO> uploadPhotosToGallery(@PathVariable Long id,
                                                            @RequestParam("images") MultipartFile[] images) {
        Optional<Gallery> opt = galleryRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        Gallery gallery = opt.get();
        List<String> photos = gallery.getPhotos();
        if (photos == null) photos = new ArrayList<>();

        try {
            for (MultipartFile mf : images) {
                if (mf == null || mf.isEmpty()) continue;
                // store to filesystem and get public path like "/uploads/uuid.ext"
                String stored = storageService.uploadImageToFileSystem(mf);
                photos.add(stored);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        gallery.setPhotos(photos);
        Gallery saved = galleryRepository.save(gallery);
        return ResponseEntity.ok(GalleryMapper.toDTO(saved));
    }
}
