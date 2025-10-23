package com.example.demo.service;

import com.example.demo.dto.FoundationDTO;

public interface FoundationService {
    FoundationDTO updateFoundation(Long foundationId, FoundationDTO foundation);
}