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
    public ResponseEntity<Donation> getDonationById(@PathVariable("id") Long donation_id) {
        return donationService.getDonationById(donation_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation) {
        return ResponseEntity.ok(donationService.createDonation(donation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donation> updateDonation(@PathVariable("id") Long donation_id,
                                                   @RequestBody Donation donation) {
        return ResponseEntity.ok(donationService.updateDonation(donation_id, donation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable("id") Long donation_id) {
        donationService.deleteDonation(donation_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/donor/{donor_id}")
    public ResponseEntity<List<Donation>> searchByDonorId(@PathVariable Long donor_id) {
        return ResponseEntity.ok(donationService.searchByDonorId(donor_id));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Donation>> searchByDate(@PathVariable Date date) {
        return ResponseEntity.ok(donationService.searchByDate(date));
    }
}