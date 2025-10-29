package com.example.demo.database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.example.demo.model.Event;
import com.example.demo.model.Gallery;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.GalleryRepository;
import jakarta.transaction.Transactional;
import java.text.SimpleDateFormat;


@Component
@Transactional
@Profile("dev")
public class DataBaseInit implements ApplicationRunner {

    @Autowired private EventRepository eventRepository;
    @Autowired private GalleryRepository galleryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        if (eventRepository.count() > 0) {
            return;
        }

        // 2) Events 
        Event event1 = new Event(
        "Jornada de Recreación con Niños",
        "Se realizó una jornada de recreación con los niños, compartiendo juegos, dinámicas y diferentes actividades al aire libre en un ambiente de alegría y convivencia.",
        new SimpleDateFormat("yyyy-MM-dd").parse("2025-09-14"), // startDate
        new SimpleDateFormat("yyyy-MM-dd").parse("2025-09-14"), // endDate
        "Mirador de la Cumbre, Cajicá",
        "19541706-46b5-4c32-b1d3-236433aab865.jpeg",
        "PLANIFICADO",
        null
        );
        eventRepository.save(event1);
        
        // 3) Example gallery seed - you can add any number of photos here
        Gallery galeria = new Gallery(
            java.util.List.of(
                "874b1eb2-9ce7-4ba3-a568-9a0b45054482.jpeg",
                "e9bf1c24-4240-4eae-8e94-3bf0edcfe159.jpeg",
                "fe4b0e96-3ebd-4cc1-b2f1-7c7c93a75615.jpeg",
                "71e201c8-838a-4a2b-8a69-e1b040bf9ff0.jpeg",
                "87b74cbb-595a-4777-b8ec-9169b2a24a12.jpeg",
                "460a46fc-022d-4a93-aa82-fdbe99c2287f.jpeg",
                "6d26d4ec-3877-4bd0-acbc-71d6d590f5d6.jpg"
            )
        );
        galleryRepository.save(galeria);

    }
}
