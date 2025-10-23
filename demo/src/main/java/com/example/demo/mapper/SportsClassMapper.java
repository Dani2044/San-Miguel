
package com.example.demo.mapper;

import com.example.demo.dto.SportsClassDTO;
import com.example.demo.model.SportsClass;

public final class SportsClassMapper {
  private SportsClassMapper() {}

  public static SportsClassDTO toDTO(SportsClass s) {
    if (s == null) return null;
    Long foundationId = (s.getFoundation() != null) ? s.getFoundation().getFoundationId() : null;
    return new SportsClassDTO(
        s.getSportsClassId(),
        s.getName(),
        s.getDescription(),
        s.getSchedule(),
        foundationId
    );
  }
}
