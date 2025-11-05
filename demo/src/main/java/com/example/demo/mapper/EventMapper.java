package com.example.demo.mapper;

import com.example.demo.dto.EventDTO;
import com.example.demo.model.Event;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class EventMapper {
  private static final ZoneId ZONE = ZoneId.of("America/Bogota");
  private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE;

  private EventMapper() {}

  public static EventDTO toDTO(Event e) {
    String event_date = null;
    String start_date = null;
    String end_date = null;
    
    if (e.getStartDate() != null) {
      event_date = e.getStartDate()
              .toInstant()
              .atZone(ZONE)
              .toLocalDate()
              .format(ISO);
      start_date = event_date;
    }
    
    if (e.getEndDate() != null) {
      end_date = e.getEndDate()
              .toInstant()
              .atZone(ZONE)
              .toLocalDate()
              .format(ISO);
    } else if (start_date != null) {
      end_date = start_date; // Default to start_date if end_date not set
    }
    
    List<String> photos = (e.getPromotionalImage() != null && !e.getPromotionalImage().isBlank())
        ? List.of(e.getPromotionalImage())
        : List.of();

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
