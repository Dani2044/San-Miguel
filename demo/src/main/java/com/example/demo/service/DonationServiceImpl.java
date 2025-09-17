package com.example.demo.service;

import com.example.demo.model.Donation;
import com.example.demo.repository.DonationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Override
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    @Override
    public Optional<Donation> getDonationById(Long donationId) {
        return donationRepository.findById(donationId);
    }

    @Override
    public Donation createDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    @Override
    public Donation updateDonation(Long donationId, Donation donation) {
        return donationRepository.findById(donationId).map(existing -> {
            existing.setAmount(donation.getAmount());
            existing.setDate(donation.getDate());
            existing.setPaymentMethod(donation.getPaymentMethod());
            existing.setPurpose(donation.getPurpose());
            existing.setFoundation(donation.getFoundation());
            existing.setDonor(donation.getDonor());
            return donationRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Donation not found with id: " + donationId));
    }

    @Override
    public void deleteDonation(Long donationId) {
        if (!donationRepository.existsById(donationId)) {
            throw new RuntimeException("Donation not found with id: " + donationId);
        }
        donationRepository.deleteById(donationId);
    }

    @Override
    public List<Donation> searchByDonorId(Long donorId) {
        return donationRepository.findByDonorDonorId(donorId);
    }

    @Override
    public List<Donation> searchByDate(Date date) {
        return donationRepository.findByDate(date);
    }
}