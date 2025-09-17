package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonationReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationReportId;
    @Column(nullable = false)
    private String usageDescription;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date reportDate;
    private String image;

    @ManyToOne
    @JoinColumn(name = "donationId")
    private Donation donation;

    @ManyToOne
    @JoinColumn(name = "foundationId")
    private Foundation foundation;

    public DonationReport(String usageDescription, Date reportDate, String image, Donation donation) {
        this.usageDescription = usageDescription;
        this.reportDate = reportDate;
        this.image = image;
        this.donation = donation;
    }
}