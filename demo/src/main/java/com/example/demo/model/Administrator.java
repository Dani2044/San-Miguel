package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Administrator {
    // TODO: Verificar que todo el esquema (modelo) sea correcto. Ejemplo: Si un campo es único, se debe poner.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long administratorId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String photo;
    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, unique = true)
    private String username;
    @Transient
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "foundationId", unique = true)
    private Foundation foundation;

    public Administrator(String username, String password, String name, String email, String photo, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.phone = phone;
    }
}