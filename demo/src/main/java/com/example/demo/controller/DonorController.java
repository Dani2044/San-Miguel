package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Donor;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserEntityRepository;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JWTGenerator;
import com.example.demo.service.DonorService;

@RestController
@RequestMapping("/api/donors")
@CrossOrigin(origins = "http://localhost:4200")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTGenerator jwtGenerator;

    // ---------- LOGIN ----------
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Donor donor) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(donor.getUsername(), donor.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    // ---------- DETAILS ----------
    @GetMapping("/details")
    public ResponseEntity<Donor> getLoggedDonor() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Donor> donor = donorService.searchByUsername(username);

        return donor.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ---------- CRUD ----------
    @GetMapping
    public List<Donor> getAll() {
        return donorService.getAllDonors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donor> getById(@PathVariable Long id) {
        return donorService.getDonorById(id)
                .map(donor -> new ResponseEntity<>(donor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestBody Donor donor,
            @RequestParam("confirm_password") String confirmPassword) {

        if (userRepository.existsByUsername(donor.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already registered");
        }

        if (!donor.getPassword().equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        UserEntity userEntity = customUserDetailsService.donorToUser(donor);
        donor.setUserEntity(userEntity);

        donorService.createDonor(donor);
        return ResponseEntity.ok("Donor created successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Donor> update(@PathVariable Long id, @RequestBody Donor updatedDonor) {
        Donor editedDonor = donorService.updateDonor(id, updatedDonor);
        return ResponseEntity.ok(editedDonor);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        donorService.deleteDonor(id);
        return ResponseEntity.ok("Donor deleted successfully");
    }

    @GetMapping("/search/email")
    public ResponseEntity<Donor> searchByEmail(@RequestParam String email) {
        Optional<Donor> donor = donorService.searchByEmail(email);
        return donor.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}