package com.example.demo.model;

import java.util.Date;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationId;
    @Column(nullable = false)
    private Float amount;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(nullable = false)
    private String paymentMethod;
    @Column(nullable = false)
    private String purpose;

    @ManyToOne
    @JoinColumn(name = "foundationId")
    private Foundation foundation;

    @ManyToOne
    @JoinColumn(name = "donorId")
    private Donor donor;

    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL)
    private List<DonationReport> donationReports;

    public Donation(Float amount, Date date, String paymentMethod, String purpose, Foundation foundation, Donor donor) {
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.purpose = purpose;
        this.foundation = foundation;
        this.donor = donor;
    }
}