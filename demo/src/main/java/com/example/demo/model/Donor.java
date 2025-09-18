package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
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
    private Long donorId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Integer phone;
    @Column(nullable = false)
    private String photo;

    @Column(unique = true, nullable = false)
    private String username;

    @Transient
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id_user")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL)
    private List<Donation> donations;

    public Donor(String name, String email, Integer phone, String photo, String username, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.photo = photo;
        this.username = username;
        this.password = password;
    }
}