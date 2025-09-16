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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(nullable = false)
    private String location;
    private String promotionalImage;
    @Column(nullable = false)
    private String status;
    private String publicationUrl;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private Foundation foundation;
}