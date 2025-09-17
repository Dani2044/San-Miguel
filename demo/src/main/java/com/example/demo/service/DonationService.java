package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Donation;

public interface DonationService {
    List<Donation> getAllDonations();
    Optional<Donation> getDonationById(Long donationId);
    Donation createDonation(Donation donation);
    Donation updateDonation(Long donationId, Donation donation);
    void deleteDonation(Long donationId);
    List<Donation> searchByDonorId(Long donorId);
    List<Donation> searchByDate(Date date);
}