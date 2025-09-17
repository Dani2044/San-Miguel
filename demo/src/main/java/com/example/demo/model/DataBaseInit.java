package com.example.demo.model;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import com.example.demo.repository.AdministratorRepository;
import com.example.demo.repository.DonationRepository;
import com.example.demo.repository.DonationReportRepository;
import com.example.demo.repository.DonorRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.FoundationRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.SportsClassRepository;
import com.example.demo.repository.UserEntityRepository;
import com.example.demo.repository.RoleRepository;

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
        // TODO: Preguntar a la fundación por todos los datos reales de cada clase y llenar la base de datos de desarrollo con esos datos.
        // 1. Create role
        Role adminRole = new Role("ADMIN");
        roleRepository.save(adminRole);

        // 2. Create foundation
        Foundation foundation = new Foundation(
                "Fundación San Miguel Fuerza de Amor",
                "Provide support, inclusion, and opportunities for vulnerable children and families in Cajicá.",
                "To be a leading organization in social transformation through love and education.",
                "Founded in Cajicá to help children and families in need, focusing on sports, education, and community development."
        );
        foundationRepository.save(foundation);

        // 3. Create UserEntity for admin
        UserEntity adminUser = UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles(Arrays.asList(adminRole))
                .build();
        userRepository.save(adminUser);

        // 4. Create Administrator and link foundation + user
        Administrator admin = new Administrator(
                "admin",
                passwordEncoder.encode("admin123"),
                "Carlos Ramírez",
                "admin@fundacionsanmiguel.org",
                "https://randomuser.me/api/portraits/men/32.jpg",
                "+57 3104567890"
        );
        admin.setUserEntity(adminUser);
        admin.setFoundation(foundation);
        administratorRepository.save(admin);

        // 5. Create sample members
        Member member1 = new Member(
                "María Gómez",
                "Coordinator",
                "Manages community programs and volunteers.",
                "https://randomuser.me/api/portraits/women/44.jpg",
                "maria.gomez@fundacionsanmiguel.org",
                "+57 3206543210",
                foundation
        );
        Member member2 = new Member(
                "Andrés Torres",
                "Sports Trainer",
                "Leads sports classes for children and young people.",
                "https://randomuser.me/api/portraits/men/50.jpg",
                "andres.torres@fundacionsanmiguel.org",
                "+57 3009876543",
                foundation
        );
        memberRepository.saveAll(Arrays.asList(member1, member2));

        // 6. Create sample sports classes
        SportsClass soccer = new SportsClass(
                "Soccer School",
                "Training program for children 7-14 years old.",
                "Mon-Wed-Fri 3:00 PM - 5:00 PM",
                foundation
        );
        SportsClass dance = new SportsClass(
                "Dance Workshop",
                "Dance and movement sessions to encourage expression and health.",
                "Tue-Thu 4:00 PM - 6:00 PM",
                foundation
        );
        sportsClassRepository.saveAll(Arrays.asList(soccer, dance));

        // 7. Create donors
        Donor donor1 = new Donor("Fundación Amigos de Cajicá", "Organization",
                "contacto@amigosdecajica.org", 1234567, false);
        donorRepository.save(donor1);

        // 8. Create donations
        Donation donation1 = new Donation(
                500000f,
                new Date(),
                "Bank Transfer",
                "Support for sports programs",
                foundation,
                donor1
        );
        donationRepository.save(donation1);

        // 9. Create donation report
        DonationReport report1 = new DonationReport(
                "The funds were used to buy soccer uniforms.",
                new Date(),
                "https://example.com/uniforms.jpg",
                donation1
        );
        donationReportRepository.save(report1);

        // 10. Create events
        Event event1 = new Event(
                "Community Integration Day",
                "An event for families to enjoy games, music, and workshops.",
                new Date(),
                new Date(),
                "Cajicá Main Park",
                "https://example.com/event1.jpg",
                "PLANNED",
                "https://facebook.com/fundacionsanmiguel/events/1",
                foundation
        );
        eventRepository.save(event1);
    }
}