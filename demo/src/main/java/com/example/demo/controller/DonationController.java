package com.example.demo.controller;

import com.example.demo.model.Donation;
import com.example.demo.service.DonationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/donations")
@CrossOrigin(origins = "http://localhost:4200")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonations() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable("id") Long donationId) {
        return donationService.getDonationById(donationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation) {
        return ResponseEntity.ok(donationService.createDonation(donation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donation> updateDonation(@PathVariable("id") Long donationId,
                                                   @RequestBody Donation donation) {
        return ResponseEntity.ok(donationService.updateDonation(donationId, donation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable("id") Long donationId) {
        donationService.deleteDonation(donationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<List<Donation>> searchByDonorId(@PathVariable Long donorId) {
        return ResponseEntity.ok(donationService.searchByDonorId(donorId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Donation>> searchByDate(@PathVariable Date date) {
        return ResponseEntity.ok(donationService.searchByDate(date));
    }
}