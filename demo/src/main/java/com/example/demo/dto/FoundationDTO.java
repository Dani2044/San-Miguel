package com.example.demo.dto;

import java.util.List;

public record FoundationDTO(
    Long id,
    String name,
    String mission,
    String vision,
    String history,
    List<Long> memberIds,
    List<Long> eventIds,
    List<Long> sportsClassIds
) {}
