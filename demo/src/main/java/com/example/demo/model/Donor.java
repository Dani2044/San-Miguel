package com.example.demo.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Donor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donor_id;
    private String name;
    private String type;
    private String email;
    private Integer phone;
    @Column(nullable = false)
    private Boolean anonymous;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL)
    private List<Donation> donations;

    public Donor(String name, String type, String email, Integer phone, Boolean anonymous) {
        this.name = name;
        this.type = type;
        this.email = email;
        this.phone = phone;
        this.anonymous = anonymous;
    }
}