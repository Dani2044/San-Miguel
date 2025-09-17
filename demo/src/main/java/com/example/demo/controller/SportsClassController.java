package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.SportsClass;
import com.example.demo.service.SportsClassService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sports-classes")
@RequiredArgsConstructor
public class SportsClassController {

    private final SportsClassService sportsClassService;

    @GetMapping
    public List<SportsClass> getAllSportsClasses() {
        return sportsClassService.getAllSportsClasses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportsClass> getSportsClassById(@PathVariable Long id) {
        return sportsClassService.getSportsClassById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SportsClass createSportsClass(@RequestBody SportsClass sportsClass) {
        return sportsClassService.createSportsClass(sportsClass);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportsClass> updateSportsClass(
            @PathVariable Long id,
            @RequestBody SportsClass sportsClass) {
        try {
            return ResponseEntity.ok(sportsClassService.updateSportsClass(id, sportsClass));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportsClass(@PathVariable Long id) {
        try {
            sportsClassService.deleteSportsClass(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{name}")
    public List<SportsClass> searchByName(@PathVariable String name) {
        return sportsClassService.searchByName(name);
    }
}