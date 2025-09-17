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
    public Optional<DonationReport> getDonationReportById(Long donationReportId) {
        return donationReportRepository.findById(donationReportId);
    }

    @Override
    public DonationReport createDonationReport(DonationReport report) {
        return donationReportRepository.save(report);
    }

    @Override
    public DonationReport updateDonationReport(Long donationReportId, DonationReport report) {
        return donationReportRepository.findById(donationReportId).map(existing -> {
            existing.setUsageDescription(report.getUsageDescription());
            existing.setReportDate(report.getReportDate());
            existing.setImage(report.getImage());
            existing.setDonation(report.getDonation());
            return donationReportRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("DonationReport not found with id: " + donationReportId));
    }

    @Override
    public void deleteDonationReport(Long donationReportId) {
        if (!donationReportRepository.existsById(donationReportId)) {
            throw new RuntimeException("DonationReport not found with id: " + donationReportId);
        }
        donationReportRepository.deleteById(donationReportId);
    }

    @Override
    public List<DonationReport> searchByDonationId(Long donationId) {
        return donationReportRepository.findByDonationDonationId(donationId);
    }

    @Override
    public List<DonationReport> searchByReportDate(Date reportDate) {
        return donationReportRepository.findByReportDate(reportDate);
    }
}