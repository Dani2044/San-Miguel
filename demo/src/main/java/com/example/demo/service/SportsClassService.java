package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.SportsClassDTO;

public interface SportsClassService {
    List<SportsClassDTO> getAllSportsClasses();
    Optional<SportsClassDTO> getSportsClassById(Long sportsClassId);
    SportsClassDTO createSportsClass(SportsClassDTO sportsClass);
    SportsClassDTO updateSportsClass(Long sportsClassId, SportsClassDTO sportsClass);
    void deleteSportsClass(Long sportsClassId);
    List<SportsClassDTO> searchByName(String name);
}