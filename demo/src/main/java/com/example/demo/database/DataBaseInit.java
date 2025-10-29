package com.example.demo.database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;
import jakarta.transaction.Transactional;
import java.text.SimpleDateFormat;


@Component
@Transactional
@Profile("dev")
public class DataBaseInit implements ApplicationRunner {

    @Autowired private EventRepository eventRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        if (eventRepository.count() > 0) {
            return;
        }

        // 2) Events 
        Event event1 = new Event(
            "Jornada de Integración Comunitaria",
            "Actividad para disfrutar en familia con juegos, música y talleres formativos.",
            new SimpleDateFormat("yyyy-MM-dd").parse("2025-12-15"), // startDate
            new SimpleDateFormat("yyyy-MM-dd").parse("2025-12-15"), // endDate
            "Parque Principal de Cajicá",
            "672eeff8-5de4-464a-b4e6-25a56b924bc0.jpeg",
            "PLANIFICADO",
            null
        );
        eventRepository.save(event1);
                
        Event event2 = new Event(
            "Torneo Comunitario de Verano",
            "Competencia deportiva abierta para la comunidad, con premiaciones y actividades familiares.",
            new SimpleDateFormat("yyyy-MM-dd").parse("2025-12-15"), // startDate
            new SimpleDateFormat("yyyy-MM-dd").parse("2025-12-16"), // endDate
            "Cancha Municipal",
            "874b1eb2-9ce7-4ba3-a568-9a0b45054482.jpeg",
            "PLANIFICADO",
            null
        );
        eventRepository.save(event2);

        
    }
}
