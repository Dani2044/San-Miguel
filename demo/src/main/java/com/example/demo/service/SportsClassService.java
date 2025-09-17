package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.SportsClass;

public interface SportsClassService {
    List<SportsClass> getAllSportsClasses();
    Optional<SportsClass> getSportsClassById(Long sportsClassId);
    SportsClass createSportsClass(SportsClass sportsClass);
    SportsClass updateSportsClass(Long sportsClassId, SportsClass sportsClass);
    void deleteSportsClass(Long sportsClassId);
    List<SportsClass> searchByName(String name);
}