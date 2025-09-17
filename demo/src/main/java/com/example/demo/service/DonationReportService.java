package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.DonationReport;

public interface DonationReportService {
    List<DonationReport> getAllDonationReports();
    Optional<DonationReport> getDonationReportById(Long donation_report_id);
    DonationReport createDonationReport(DonationReport report);
    DonationReport updateDonationReport(Long donation_report_id, DonationReport report);
    void deleteDonationReport(Long donation_report_id);
    List<DonationReport> searchByDonationId(Long donation_id);
    List<DonationReport> searchByReportDate(Date report_date);
}