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
    String date = null;
    if (e.getStartDate() != null) {
      date = e.getStartDate()
              .toInstant()
              .atZone(ZONE)
              .toLocalDate()
              .format(ISO);
    }
    List<String> photos = (e.getPromotionalImage() != null && !e.getPromotionalImage().isBlank())
        ? List.of(e.getPromotionalImage())
        : List.of();

    return new EventDTO(
        e.getEventId(),
        e.getTitle(),
        e.getDescription(),
        date,
        e.getLocation(),
        photos
    );
  }
}
