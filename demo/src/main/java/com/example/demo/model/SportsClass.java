package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SportsClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sportsClassId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String schedule;

    @ManyToOne
    @JoinColumn(name = "foundationId")
    private Foundation foundation;

    public SportsClass(String name, String description, String schedule, Foundation foundation) {
        this.name = name;
        this.description = description;
        this.schedule = schedule;
        this.foundation = foundation;
    }
}