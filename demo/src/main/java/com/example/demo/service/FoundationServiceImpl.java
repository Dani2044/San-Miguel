package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.Foundation;
import com.example.demo.repository.FoundationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoundationServiceImpl implements FoundationService {

    private final FoundationRepository foundationRepository;

    @Override
    public Foundation updateFoundation(Long foundationId, Foundation foundation) {
        return foundationRepository.findById(foundationId)
                .map(existingFoundation -> {
                    existingFoundation.setName(foundation.getName());
                    existingFoundation.setMission(foundation.getMission());
                    existingFoundation.setVision(foundation.getVision());
                    existingFoundation.setHistory(foundation.getHistory());
                    return foundationRepository.save(existingFoundation);
                })
                .orElseThrow(() -> new RuntimeException("Foundation not found with id: " + foundationId));
    }
}