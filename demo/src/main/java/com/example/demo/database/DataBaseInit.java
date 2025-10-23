package com.example.demo.database;
import java.util.Arrays;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.example.demo.model.Event;
import com.example.demo.model.Foundation;
import com.example.demo.model.Member;
import com.example.demo.model.SportsClass;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.FoundationRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.SportsClassRepository;
import jakarta.transaction.Transactional;
import java.text.SimpleDateFormat;


@Component
@Transactional
@Profile("dev")
public class DataBaseInit implements ApplicationRunner {

    @Autowired private EventRepository eventRepository;
    @Autowired private FoundationRepository foundationRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private SportsClassRepository sportsClassRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Avoid duplicating seed data on each startup
        if (foundationRepository.count() > 0) {
            return;
        }

        // 1) Foundation 
        // Store empty strings for textual foundation fields; frontend will render static content.
        Foundation foundation = new Foundation(
            "",
            "",
            "",
            ""
        );
        foundationRepository.save(foundation);

        // 2) Events 
        Event event1 = new Event(
            "Jornada de Integración Comunitaria",
            "Actividad para disfrutar en familia con juegos, música y talleres formativos.",
            new Date(),
            new Date(),
            "Parque Principal de Cajicá",
            "672eeff8-5de4-464a-b4e6-25a56b924bc0.jpeg",
            "PLANIFICADO", 
            "https://facebook.com/fundacionsanmiguel/events/1",
            foundation
        );
        eventRepository.save(event1);
        // ... dentro del método run(...)

        // nuevo evento seed
        Event event2 = new Event(
            "Torneo Comunitario de Verano",
            "Competencia deportiva abierta para la comunidad, con premiaciones y actividades familiares.",
            new SimpleDateFormat("yyyy-MM-dd").parse("2025-12-15"), // startDate
            new SimpleDateFormat("yyyy-MM-dd").parse("2025-12-16"), // endDate
            "Cancha Municipal",
            "https://example.com/evento2.jpg",
            "PLANIFICADO",
            "https://facebook.com/fundacionsanmiguel/events/2",
            foundation
        );
        eventRepository.save(event2);

        // 3) Members
        Member member1 = new Member(
            "María Gómez",
            "Coordinadora",
            "Gestiona programas comunitarios y el voluntariado.",
            "https://randomuser.me/api/portraits/women/44.jpg",
            "maria.gomez@fundacionsanmiguel.org",
            "+57 3206543210",
            foundation
        );
        Member member2 = new Member(
            "Andrés Torres",
            "Entrenador Deportivo",
            "Dirige clases formativas para niños, niñas y jóvenes.",
            "https://randomuser.me/api/portraits/men/50.jpg",
            "andres.torres@fundacionsanmiguel.org",
            "+57 3009876543",
            foundation
        );
        memberRepository.saveAll(Arrays.asList(member1, member2));

        // 4) Sports classes 
        SportsClass soccer = new SportsClass(
            "Escuela de Fútbol",
            "Entrenamiento formativo para niñas y niños de 7 a 14 años.",
            "Lun–Mié–Vie 3:00 p. m. – 5:00 p. m.",
            foundation
        );
        SportsClass dance = new SportsClass(
            "Taller de Danza",
            "Sesiones de expresión corporal y ritmo para la salud y el bienestar.",
            "Mar–Jue 4:00 p. m. – 6:00 p. m.",
            foundation
        );
        sportsClassRepository.saveAll(Arrays.asList(soccer, dance));
    }
}
