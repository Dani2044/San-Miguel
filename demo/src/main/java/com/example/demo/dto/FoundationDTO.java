package com.example.demo.dto;

import java.util.List;

public record FoundationDTO(
    Long id,
    List<Long> memberIds,
    List<Long> eventIds,
    List<Long> sportsClassIds
) {}
