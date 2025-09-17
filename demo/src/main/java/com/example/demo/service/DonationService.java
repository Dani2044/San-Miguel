package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Donation;

public interface DonationService {
    List<Donation> getAllDonations();
    Optional<Donation> getDonationById(Long donation_id);
    Donation createDonation(Donation donation);
    Donation updateDonation(Long donation_id, Donation donation);
    void deleteDonation(Long donation_id);
    List<Donation> searchByDonorId(Long donor_id);
    List<Donation> searchByDate(Date date);
}