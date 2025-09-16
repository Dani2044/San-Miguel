package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Foundation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String mission;
    @Column(nullable = false)
    private String vision;
    @Column(nullable = false)
    private String history;

    @OneToOne(mappedBy = "foundation", cascade = CascadeType.ALL)
    private Administrator administrator;

    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL)
    private List<Member> members;

    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL)
    private List<Donation> donations;

    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL)
    private List<Event> events;

    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL)
    private List<DonationReport> donationReports;

    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL)
    private List<SportsClass> sportsClasses;
}