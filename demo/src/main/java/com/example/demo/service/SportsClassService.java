package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.SportsClass;

public interface SportsClassService {
    List<SportsClass> getAllSportsClasses();
    Optional<SportsClass> getSportsClassById(Long sports_class_id);
    SportsClass createSportsClass(SportsClass sportsClass);
    SportsClass updateSportsClass(Long sports_class_id, SportsClass sportsClass);
    void deleteSportsClass(Long sports_class_id);
    List<SportsClass> searchByName(String name);
}