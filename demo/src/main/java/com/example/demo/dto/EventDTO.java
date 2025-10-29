package com.example.demo.dto;

import java.util.List;

public record EventDTO(
    Long id,
    String title,
    String description,
    String event_date,
    String location,
    List<String> photos
) {}