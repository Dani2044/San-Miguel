package com.example.demo.dto;

public record SportsClassDTO(
    Long id,
    String name,
    String description,
    String schedule,
    Long foundationId
) {}
