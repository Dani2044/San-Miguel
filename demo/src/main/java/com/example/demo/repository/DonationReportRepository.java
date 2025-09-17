package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DonationReport;

@Repository
public interface DonationReportRepository extends JpaRepository<DonationReport, Long> {
    List<DonationReport> findByDonationDonationId(Long donation_id);
    List<DonationReport> findByReportDate(Date report_date);
}