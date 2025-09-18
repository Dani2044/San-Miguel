package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Donor;

public interface DonorService {
    List<Donor> getAllDonors();
    Optional<Donor> getDonorById(Long id);
    Optional<Donor> searchByUsername(String username);
    Donor createDonor(Donor donor);
    Donor updateDonor(Long id, Donor donor);
    void deleteDonor(Long id);
    Optional<Donor> searchByEmail(String email);
    List<Donor> searchByName(String name);
}