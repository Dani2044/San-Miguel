package com.example.demo.controller;

import com.example.demo.model.DonationReport;
import com.example.demo.service.DonationReportService;

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
@RequestMapping("/donation-reports")
@CrossOrigin(origins = "http://localhost:4200")
public class DonationReportController {

    @Autowired
    private DonationReportService donationReportService;

    @GetMapping
    public ResponseEntity<List<DonationReport>> getAllDonationReports() {
        return ResponseEntity.ok(donationReportService.getAllDonationReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationReport> getDonationReportById(@PathVariable("id") Long donationReportId) {
        return donationReportService.getDonationReportById(donationReportId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DonationReport> createDonationReport(@RequestBody DonationReport report) {
        return ResponseEntity.ok(donationReportService.createDonationReport(report));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonationReport> updateDonationReport(@PathVariable("id") Long donationReportId,
                                                               @RequestBody DonationReport report) {
        return ResponseEntity.ok(donationReportService.updateDonationReport(donationReportId, report));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonationReport(@PathVariable("id") Long donationReportId) {
        donationReportService.deleteDonationReport(donationReportId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/donation/{donationId}")
    public ResponseEntity<List<DonationReport>> searchByDonationId(@PathVariable Long donationId) {
        return ResponseEntity.ok(donationReportService.searchByDonationId(donationId));
    }

    @GetMapping("/date/{reportDate}")
    public ResponseEntity<List<DonationReport>> searchByReportDate(@PathVariable Date reportDate) {
        return ResponseEntity.ok(donationReportService.searchByReportDate(reportDate));
    }
}