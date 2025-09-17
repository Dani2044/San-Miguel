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
    private Long donation_report_id;
    @Column(nullable = false)
    private String usage_description;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date report_date;
    private String image;

    @ManyToOne
    @JoinColumn(name = "donation_id")
    private Donation donation;

    public DonationReport(String usage_description, Date report_date, String image, Donation donation) {
        this.usage_description = usage_description;
        this.report_date = report_date;
        this.image = image;
        this.donation = donation;
    }
}