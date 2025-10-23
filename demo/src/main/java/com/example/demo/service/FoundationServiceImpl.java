package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.dto.FoundationDTO;
import com.example.demo.mapper.FoundationMapper;
import com.example.demo.model.Foundation;
import com.example.demo.repository.FoundationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoundationServiceImpl implements FoundationService {

    private final FoundationRepository foundationRepository;

    @Override
    public FoundationDTO updateFoundation(Long foundationId, FoundationDTO foundation) {
        Foundation updated = foundationRepository.findById(foundationId)
                .map(existingFoundation -> {
                    existingFoundation.setName(foundation.name());
                    existingFoundation.setMission(foundation.mission());
                    existingFoundation.setVision(foundation.vision());
                    existingFoundation.setHistory(foundation.history());
                    return foundationRepository.save(existingFoundation);
                })
                .orElseThrow(() -> new RuntimeException("Foundation not found with id: " + foundationId));

        return FoundationMapper.toDTO(updated);
    }
}