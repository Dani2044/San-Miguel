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
    private Long event_id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date start_date;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date end_date;
    @Column(nullable = false)
    private String location;
    private String promotional_image;
    @Column(nullable = false)
    private String status;
    private String publication_url;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private Foundation foundation;

    public Event(String title, String description, Date start_date, Date end_date, String location, String promotional_image, String status, String publication_url, Foundation foundation) {
        this.title = title;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.location = location;
        this.promotional_image = promotional_image;
        this.status = status;
        this.publication_url = publication_url;
        this.foundation = foundation;
    }
}