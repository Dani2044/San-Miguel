package com.example.demo.dto;

public record MemberDTO(
    Long id,
    String name,
    String position,
    String responsibilities,
    String photo,
    String email,
    String phone,
    Long foundationId
) {}
