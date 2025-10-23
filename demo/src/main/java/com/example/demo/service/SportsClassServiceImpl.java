package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.SportsClassDTO;
import com.example.demo.mapper.SportsClassMapper;
import com.example.demo.model.Foundation;
import com.example.demo.model.SportsClass;
import com.example.demo.repository.FoundationRepository;
import com.example.demo.repository.SportsClassRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SportsClassServiceImpl implements SportsClassService {

    private final SportsClassRepository sportsClassRepository;
    private final FoundationRepository foundationRepository;

    @Override
    public List<SportsClassDTO> getAllSportsClasses() {
        return sportsClassRepository.findAll().stream().map(SportsClassMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<SportsClassDTO> getSportsClassById(Long sportsClassId) {
        return sportsClassRepository.findById(sportsClassId).map(SportsClassMapper::toDTO);
    }

    @Override
    public SportsClassDTO createSportsClass(SportsClassDTO dto) {
        SportsClass s = SportsClass.builder()
                .name(dto.name())
                .description(dto.description())
                .schedule(dto.schedule())
                .build();

        if (dto.foundationId() != null) {
            Foundation f = foundationRepository.findById(dto.foundationId())
                    .orElseThrow(() -> new RuntimeException("Foundation not found: " + dto.foundationId()));
            s.setFoundation(f);
        }

        SportsClass saved = sportsClassRepository.save(s);
        return SportsClassMapper.toDTO(saved);
    }

    @Override
    public SportsClassDTO updateSportsClass(Long sportsClassId, SportsClassDTO dto) {
        return sportsClassRepository.findById(sportsClassId)
                .map(existing -> {
                    existing.setName(dto.name());
                    existing.setDescription(dto.description());
                    existing.setSchedule(dto.schedule());
                    if (dto.foundationId() != null) {
                        Foundation f = foundationRepository.findById(dto.foundationId())
                                .orElseThrow(() -> new RuntimeException("Foundation not found: " + dto.foundationId()));
                        existing.setFoundation(f);
                    } else {
                        existing.setFoundation(null);
                    }
                    return SportsClassMapper.toDTO(sportsClassRepository.save(existing));
                })
                .orElseThrow(() -> new RuntimeException("SportsClass not found with id: " + sportsClassId));
    }

    @Override
    public void deleteSportsClass(Long sportsClassId) {
        if (!sportsClassRepository.existsById(sportsClassId)) {
            throw new RuntimeException("SportsClass not found with id: " + sportsClassId);
        }
        sportsClassRepository.deleteById(sportsClassId);
    }

    @Override
    public List<SportsClassDTO> searchByName(String name) {
        return sportsClassRepository.findByNameContainingIgnoreCase(name).stream().map(SportsClassMapper::toDTO).collect(Collectors.toList());
    }
}