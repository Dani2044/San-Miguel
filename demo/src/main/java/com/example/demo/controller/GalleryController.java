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
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
            // Crear una galería vacía automáticamente si no existe
            Gallery newGallery = new Gallery();
            newGallery.setPhotos(new ArrayList<>());
            Gallery saved = galleryRepository.save(newGallery);
            return ResponseEntity.ok(GalleryMapper.toDTO(saved));
        }
        return ResponseEntity.ok(GalleryMapper.toDTO(all.get(0)));
    }

    @GetMapping("/galleries")
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
    @PostMapping(value = "/{id}/photos", consumes = {"multipart/form-data"})
    public ResponseEntity<GalleryDTO> uploadPhotosToGallery(
            @PathVariable Long id,
            @RequestParam("images") MultipartFile[] images) {
        
        // Validar que la galería existe
        Optional<Gallery> opt = galleryRepository.findById(id);
        if (opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Galería no encontrada con ID: " + id);
        }
        
        Gallery gallery = opt.get();
        List<String> photos = gallery.getPhotos();
        if (photos == null) {
            photos = new ArrayList<>();
        }

        // Validar que hay archivos
        if (images == null || images.length == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se proporcionaron archivos");
        }

        // Tipos MIME permitidos
        List<String> allowedMimeTypes = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
        );
        
        // Tamaño máximo: 10MB
        long maxSize = 10 * 1024 * 1024; // 10MB en bytes

        List<String> uploadedFiles = new ArrayList<>();
        
        try {
            for (MultipartFile file : images) {
                if (file == null || file.isEmpty()) {
                    continue;
                }

                // Validar tipo MIME
                String contentType = file.getContentType();
                if (contentType == null || !allowedMimeTypes.contains(contentType.toLowerCase())) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, 
                        "Tipo de archivo no permitido: " + contentType + 
                        ". Tipos permitidos: " + String.join(", ", allowedMimeTypes)
                    );
                }

                // Validar tamaño
                if (file.getSize() > maxSize) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Archivo demasiado grande: " + file.getOriginalFilename() + 
                        ". Tamaño máximo: 10MB"
                    );
                }

                // Guardar archivo (devuelve solo el filename sin prefijo /uploads/)
                String stored = storageService.uploadImageToFileSystem(file);
                
                // Evitar duplicados
                if (!uploadedFiles.contains(stored) && !photos.contains(stored)) {
                    uploadedFiles.add(stored);
                }
            }

            // Si no se subió ningún archivo válido
            if (uploadedFiles.isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, 
                    "No se pudo subir ningún archivo válido"
                );
            }

            // Agregar las nuevas fotos a la lista existente
            photos.addAll(uploadedFiles);
            gallery.setPhotos(photos);
            
            Gallery saved = galleryRepository.save(gallery);
            return ResponseEntity.ok(GalleryMapper.toDTO(saved));
            
        } catch (ResponseStatusException e) {
            // Re-lanzar excepciones de estado HTTP (ya tienen el código correcto)
            throw e;
        } catch (IOException e) {
            // Error al guardar archivos
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error al guardar los archivos: " + e.getMessage()
            );
        } catch (Exception e) {
            // Cualquier otro error
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error inesperado al subir fotos: " + e.getMessage()
            );
        }
    }

    /**
     * Delete a photo from a gallery by updating the photo list.
     */
    @PutMapping("/{id}/photos")
    public ResponseEntity<GalleryDTO> updateGalleryPhotos(@PathVariable Long id, @RequestBody GalleryDTO dto) {
        Optional<Gallery> opt = galleryRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        Gallery gallery = opt.get();
        gallery.setPhotos(dto.photos() != null ? dto.photos() : new ArrayList<>());
        Gallery saved = galleryRepository.save(gallery);
        return ResponseEntity.ok(GalleryMapper.toDTO(saved));
    }
}
