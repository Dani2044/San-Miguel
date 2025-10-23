package com.example.demo.mapper;

import com.example.demo.dto.FoundationDTO;
import com.example.demo.model.Foundation;
import java.util.List;
import java.util.stream.Collectors;

public final class FoundationMapper {
  private FoundationMapper() {}

  public static FoundationDTO toDTO(Foundation f) {
    if (f == null) return null;
    List<Long> memberIds = (f.getMembers() == null) ? List.of() : f.getMembers().stream()
        .map(m -> m.getMemberId()).collect(Collectors.toList());
    List<Long> eventIds = (f.getEvents() == null) ? List.of() : f.getEvents().stream()
        .map(e -> e.getEventId()).collect(Collectors.toList());
    List<Long> sportsClassIds = (f.getSportsClasses() == null) ? List.of() : f.getSportsClasses().stream()
        .map(s -> s.getSportsClassId()).collect(Collectors.toList());

    return new FoundationDTO(
        f.getFoundationId(),
        f.getName(),
        f.getMission(),
        f.getVision(),
        f.getHistory(),
        memberIds,
        eventIds,
        sportsClassIds
    );
  }
}
