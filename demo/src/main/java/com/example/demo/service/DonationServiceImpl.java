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
    public Optional<Donation> getDonationById(Long donation_id) {
        return donationRepository.findById(donation_id);
    }

    @Override
    public Donation createDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    @Override
    public Donation updateDonation(Long donation_id, Donation donation) {
        return donationRepository.findById(donation_id).map(existing -> {
            existing.setAmount(donation.getAmount());
            existing.setDate(donation.getDate());
            existing.setPayment_method(donation.getPayment_method());
            existing.setPurpose(donation.getPurpose());
            existing.setFoundation(donation.getFoundation());
            existing.setDonor(donation.getDonor());
            return donationRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Donation not found with id: " + donation_id));
    }

    @Override
    public void deleteDonation(Long donation_id) {
        if (!donationRepository.existsById(donation_id)) {
            throw new RuntimeException("Donation not found with id: " + donation_id);
        }
        donationRepository.deleteById(donation_id);
    }

    @Override
    public List<Donation> searchByDonorId(Long donor_id) {
        return donationRepository.findByDonorDonorId(donor_id);
    }

    @Override
    public List<Donation> searchByDate(Date date) {
        return donationRepository.findByDate(date);
    }
}