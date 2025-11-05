package com.example.demo.controller;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Event;
import com.example.demo.service.EventService;
import com.example.demo.service.StorageService;
import com.example.demo.dto.EventDTO;
import com.example.demo.mapper.EventMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final StorageService storageService;

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents().stream()
                .map(EventMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("id") Long id) {
        return eventService.getEventById(id)
                .map(EventMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        try {
            Event event = fromDTO(eventDTO);
            Event created = eventService.createEvent(event);
            return ResponseEntity.ok(EventMapper.toDTO(created));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable("id") Long id, @RequestBody EventDTO eventDTO) {
        try {
            Event event = fromDTO(eventDTO);
            Event updated = eventService.updateEvent(id, event);
            return ResponseEntity.ok(EventMapper.toDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/title")
    public List<EventDTO> searchByTitle(@RequestParam String title) {
        return eventService.searchByTitle(title).stream()
                .map(EventMapper::toDTO)
                .toList();
    }

    @GetMapping("/search/status")
    public List<EventDTO> searchByStatus(@RequestParam String status) {
        return eventService.searchByStatus(status).stream()
                .map(EventMapper::toDTO)
                .toList();
    }

    @GetMapping("/search/startDate")
    public List<EventDTO> searchByStartDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return eventService.searchByStartDate(startDate).stream()
                .map(EventMapper::toDTO)
                .toList();
    }

    @GetMapping("/search/location")
    public List<EventDTO> searchByLocation(@RequestParam String location) {
        return eventService.searchByLocation(location).stream()
                .map(EventMapper::toDTO)
                .toList();
    }

    /**
     * Upload one or more images to an event.
     * Adds the uploaded image URLs to the event's photos list.
     */
    @PostMapping("/{id}/photos")
    public ResponseEntity<EventDTO> uploadPhotosToEvent(@PathVariable Long id,
                                                         @RequestParam("images") MultipartFile[] images) {
        try {
            Event event = eventService.getEventById(id)
                    .orElseThrow(() -> new RuntimeException("Event not found"));
            
            List<String> photos = new ArrayList<>();
            if (event.getPromotionalImage() != null && !event.getPromotionalImage().isBlank()) {
                photos.add(event.getPromotionalImage());
            }
            
            for (MultipartFile mf : images) {
                if (mf == null || mf.isEmpty()) continue;
                // Store to filesystem and get public path like "/uploads/uuid.ext"
                String stored = storageService.uploadImageToFileSystem(mf);
                if (!photos.contains(stored)) {
                    photos.add(stored);
                }
            }
            
            // Update promotional image to first photo if exists
            String promotionalImage = photos.isEmpty() ? null : photos.get(0);
            event.setPromotionalImage(promotionalImage);
            
            Event updated = eventService.updateEvent(id, event);
            return ResponseEntity.ok(EventMapper.toDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Event fromDTO(EventDTO dto) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Date startDate = null;
        // Check start_date first, then event_date as fallback
        String dateStr = dto.start_date();
        if (dateStr == null || dateStr.isEmpty()) {
            dateStr = dto.event_date();
        }
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                startDate = sdf.parse(dateStr);
            } catch (ParseException e) {
                // Ignore parsing errors
            }
        }
        
        Date endDate = startDate; // Default to same as start date
        // Check end_date if provided
        String endDateStr = dto.end_date();
        if (endDateStr != null && !endDateStr.isEmpty()) {
            try {
                endDate = sdf.parse(endDateStr);
            } catch (ParseException e) {
                // Ignore parsing errors, keep default
            }
        }
        
        // Use promotionalImage from first photo if photos exist
        String promotionalImage = null;
        if (dto.photos() != null && !dto.photos().isEmpty()) {
            String photoPath = dto.photos().get(0);
            // Extract filename from path (remove /uploads/ prefix if present)
            if (photoPath != null) {
                if (photoPath.startsWith("/uploads/")) {
                    promotionalImage = photoPath.substring("/uploads/".length());
                } else if (photoPath.startsWith("uploads/")) {
                    promotionalImage = photoPath.substring("uploads/".length());
                } else {
                    promotionalImage = photoPath;
                }
            }
        }
        
        // Default status if not provided
        String status = "PLANIFICADO";
        
        Event event = Event.builder()
                .title(dto.title())
                .description(dto.description() != null ? dto.description() : "")
                .startDate(startDate)
                .endDate(endDate)
                .location(dto.location() != null ? dto.location() : "")
                .promotionalImage(promotionalImage)
                .status(status)
                .foundation(null) // Can be set later if needed
                .build();
        
        return event;
    }
}