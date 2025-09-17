package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import com.example.demo.repository.AdministratorRepository;
import com.example.demo.repository.DonationReportRepository;
import com.example.demo.repository.DonationRepository;
import com.example.demo.repository.DonorRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.FoundationRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.SportsClassRepository;
import com.example.demo.repository.UserEntityRepository;

import jakarta.transaction.Transactional;

@Controller
@Transactional
@Profile("dev")
public class DataBaseInit implements ApplicationRunner {
    @Autowired
    AdministratorRepository administratorRepository;

    @Autowired
    DonationRepository donationRepository;

    @Autowired
    DonationReportRepository donationReportRepository;

    @Autowired
    DonorRepository donorRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    FoundationRepository foundationRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SportsClassRepository sportsClassRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserEntityRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Role creation
        roleRepository.save(new Role("ROLE_ADMIN"));
    }
}
