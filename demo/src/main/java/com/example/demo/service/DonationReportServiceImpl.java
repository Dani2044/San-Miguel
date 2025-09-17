package com.example.demo.service;

import com.example.demo.model.DonationReport;
import com.example.demo.repository.DonationReportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DonationReportServiceImpl implements DonationReportService {
    
    @Autowired
    private DonationReportRepository donationReportRepository;

    @Override
    public List<DonationReport> getAllDonationReports() {
        return donationReportRepository.findAll();
    }

    @Override
    public Optional<DonationReport> getDonationReportById(Long donation_report_id) {
        return donationReportRepository.findById(donation_report_id);
    }

    @Override
    public DonationReport createDonationReport(DonationReport report) {
        return donationReportRepository.save(report);
    }

    @Override
    public DonationReport updateDonationReport(Long donation_report_id, DonationReport report) {
        return donationReportRepository.findById(donation_report_id).map(existing -> {
            existing.setUsage_description(report.getUsage_description());
            existing.setReport_date(report.getReport_date());
            existing.setImage(report.getImage());
            existing.setDonation(report.getDonation());
            return donationReportRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("DonationReport not found with id: " + donation_report_id));
    }

    @Override
    public void deleteDonationReport(Long donation_report_id) {
        if (!donationReportRepository.existsById(donation_report_id)) {
            throw new RuntimeException("DonationReport not found with id: " + donation_report_id);
        }
        donationReportRepository.deleteById(donation_report_id);
    }

    @Override
    public List<DonationReport> searchByDonationId(Long donation_id) {
        return donationReportRepository.findByDonationDonationId(donation_id);
    }

    @Override
    public List<DonationReport> searchByReportDate(Date report_date) {
        return donationReportRepository.findByReportDate(report_date);
    }
}