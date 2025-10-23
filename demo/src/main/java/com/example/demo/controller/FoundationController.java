package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FoundationDTO;
import com.example.demo.service.FoundationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/foundation")
@RequiredArgsConstructor
public class FoundationController {

    private final FoundationService foundationService;

    @PutMapping("/{id}")
    public ResponseEntity<FoundationDTO> updateFoundation(
            @PathVariable Long id,
            @RequestBody FoundationDTO foundation) {
        try {
            FoundationDTO updatedFoundation = foundationService.updateFoundation(id, foundation);
            return ResponseEntity.ok(updatedFoundation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}