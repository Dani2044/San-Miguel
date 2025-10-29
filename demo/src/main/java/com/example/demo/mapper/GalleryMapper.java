package com.example.demo.mapper;
import com.example.demo.dto.GalleryDTO;
import com.example.demo.model.Gallery;
import java.util.List;
public class GalleryMapper {

    public static GalleryDTO toDTO(Gallery gallery) {
        if (gallery == null) return null;
        List<String> photos = gallery.getPhotos();
        if (photos == null) photos = List.of();
        return new GalleryDTO(gallery.getGalleryId(), photos);
    }
}
