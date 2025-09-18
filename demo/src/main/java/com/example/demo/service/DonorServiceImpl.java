package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Donor;
import com.example.demo.repository.DonorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;

    @Override
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    @Override
    public Optional<Donor> getDonorById(Long id) {
        return donorRepository.findById(id);
    }

    @Override
    public Optional<Donor> searchByUsername(String username) {
        return donorRepository.findByUsername(username);
    }

    @Override
    public Donor createDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    @Override
    public Donor updateDonor(Long id, Donor donor) {
        return donorRepository.findById(id).map(existingDonor -> {
            existingDonor.setName(donor.getName());
            existingDonor.setEmail(donor.getEmail());
            existingDonor.setPhone(donor.getPhone());
            existingDonor.setPhoto(donor.getPhoto());
            existingDonor.setUsername(donor.getUsername());
            existingDonor.setPassword(donor.getPassword());
            return donorRepository.save(existingDonor);
        }).orElseThrow(() -> new RuntimeException("Donor not found with id: " + id));
    }

    @Override
    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }

    @Override
    public Optional<Donor> searchByEmail(String email) {
        return donorRepository.findByEmail(email);
    }

    @Override
    public List<Donor> searchByName(String name) {
        return donorRepository.findByNameContainingIgnoreCase(name);
    }
}