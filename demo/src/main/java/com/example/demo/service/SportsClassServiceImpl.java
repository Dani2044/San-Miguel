package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.SportsClass;
import com.example.demo.repository.SportsClassRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SportsClassServiceImpl implements SportsClassService {

    private final SportsClassRepository sportsClassRepository;

    @Override
    public List<SportsClass> getAllSportsClasses() {
        return sportsClassRepository.findAll();
    }

    @Override
    public Optional<SportsClass> getSportsClassById(Long sportsClassId) {
        return sportsClassRepository.findById(sportsClassId);
    }

    @Override
    public SportsClass createSportsClass(SportsClass sportsClass) {
        return sportsClassRepository.save(sportsClass);
    }

    @Override
    public SportsClass updateSportsClass(Long sportsClassId, SportsClass sportsClass) {
        return sportsClassRepository.findById(sportsClassId)
                .map(existingClass -> {
                    existingClass.setName(sportsClass.getName());
                    existingClass.setDescription(sportsClass.getDescription());
                    existingClass.setSchedule(sportsClass.getSchedule());
                    existingClass.setFoundation(sportsClass.getFoundation());
                    return sportsClassRepository.save(existingClass);
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
    public List<SportsClass> searchByName(String name) {
        return sportsClassRepository.findByNameContainingIgnoreCase(name);
    }
}