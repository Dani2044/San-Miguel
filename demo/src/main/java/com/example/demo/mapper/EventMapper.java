package com.example.demo.mapper;

import com.example.demo.dto.EventDTO;
import com.example.demo.model.Event;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public final class EventMapper {
  private static final ZoneId ZONE = ZoneId.of("America/Bogota");
  private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE;

  private EventMapper() {}

  public static EventDTO toDTO(Event e) {
    String event_date = null;
    String start_date = null;
    String end_date = null;
    
    if (e.getStartDate() != null) {
      // Convert java.sql.Date to LocalDate safely
      // java.sql.Date doesn't support toInstant(), so use getTime() and convert
      Date date = e.getStartDate();
      LocalDate localDate = Instant.ofEpochMilli(date.getTime())
              .atZone(ZONE)
              .toLocalDate();
      event_date = localDate.format(ISO);
      start_date = event_date;
    }
    
    if (e.getEndDate() != null) {
      Date date = e.getEndDate();
      LocalDate localDate = Instant.ofEpochMilli(date.getTime())
              .atZone(ZONE)
              .toLocalDate();
      end_date = localDate.format(ISO);
    } else if (start_date != null) {
      end_date = start_date; // Default to start_date if end_date not set
    }
    
    // Convert promotionalImage to photos array
    // promotionalImage is stored as filename only (no /uploads/ prefix)
    List<String> photos = new ArrayList<>();
    if (e.getPromotionalImage() != null && !e.getPromotionalImage().isBlank()) {
        // Remove /uploads/ prefix if present (backward compatibility)
        String imagePath = e.getPromotionalImage();
        if (imagePath.startsWith("/uploads/")) {
            imagePath = imagePath.substring("/uploads/".length());
        } else if (imagePath.startsWith("uploads/")) {
            imagePath = imagePath.substring("uploads/".length());
        }
        photos.add(imagePath);
    }

  return new EventDTO(
    e.getEventId(),
    e.getTitle(),
    e.getDescription(),
    event_date,
    start_date,
    end_date,
    e.getLocation(),
  photos
  );
  }
}
