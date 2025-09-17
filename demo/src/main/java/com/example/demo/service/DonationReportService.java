package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.DonationReport;

public interface DonationReportService {
    List<DonationReport> getAllDonationReports();
    Optional<DonationReport> getDonationReportById(Long donationReportId);
    DonationReport createDonationReport(DonationReport report);
    DonationReport updateDonationReport(Long donationReportId, DonationReport report);
    void deleteDonationReport(Long donationReportId);
    List<DonationReport> searchByDonationId(Long donationId);
    List<DonationReport> searchByReportDate(Date reportDate);
}