package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String position;
    @Column(nullable = false)
    private String responsibilities;
    @Column(nullable = false)
    private String photo;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "foundationId")
    private Foundation foundation;

    public Member(String name, String position, String responsibilities, String photo, String email, String phone, Foundation foundation) {
        this.name = name;
        this.position = position;
        this.responsibilities = responsibilities;
        this.photo = photo;
        this.email = email;
        this.phone = phone;
        this.foundation = foundation;
    }
}