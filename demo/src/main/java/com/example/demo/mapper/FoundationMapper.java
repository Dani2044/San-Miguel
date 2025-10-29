package com.example.demo.mapper;

import com.example.demo.dto.FoundationDTO;
import com.example.demo.model.Foundation;
import java.util.List;
import java.util.stream.Collectors;

public final class FoundationMapper {
  private FoundationMapper() {}

  public static FoundationDTO toDTO(Foundation f) {
    if (f == null) return null;
    List<Long> eventIds = (f.getEvents() == null) ? List.of() : f.getEvents().stream()
        .map(e -> e.getEventId()).collect(Collectors.toList());

  return new FoundationDTO(
    f.getFoundationId(),
    eventIds
  );
  }
}
