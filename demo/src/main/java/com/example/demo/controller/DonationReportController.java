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
    public ResponseEntity<DonationReport> getDonationReportById(@PathVariable("id") Long donation_report_id) {
        return donationReportService.getDonationReportById(donation_report_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DonationReport> createDonationReport(@RequestBody DonationReport report) {
        return ResponseEntity.ok(donationReportService.createDonationReport(report));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonationReport> updateDonationReport(@PathVariable("id") Long donation_report_id,
                                                               @RequestBody DonationReport report) {
        return ResponseEntity.ok(donationReportService.updateDonationReport(donation_report_id, report));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonationReport(@PathVariable("id") Long donation_report_id) {
        donationReportService.deleteDonationReport(donation_report_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/donation/{donation_id}")
    public ResponseEntity<List<DonationReport>> searchByDonationId(@PathVariable Long donation_id) {
        return ResponseEntity.ok(donationReportService.searchByDonationId(donation_id));
    }

    @GetMapping("/date/{report_date}")
    public ResponseEntity<List<DonationReport>> searchByReportDate(@PathVariable Date report_date) {
        return ResponseEntity.ok(donationReportService.searchByReportDate(report_date));
    }
}