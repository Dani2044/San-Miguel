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


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Foundation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foundationId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String mission;
    @Column(nullable = false)
    private String vision;
    @Column(nullable = false)
    private String history;

    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL)
    private List<Member> members;


    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL)
    private List<Event> events;


    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL)
    private List<SportsClass> sportsClasses;

    public Foundation(String name, String mission, String vision, String history) {
        this.name = name;
        this.mission = mission;
        this.vision = vision;
        this.history = history;
    }
}