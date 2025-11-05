package com.example.demo.database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.demo.model.Event;
import com.example.demo.model.Gallery;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.GalleryRepository;
import com.example.demo.repository.UserEntityRepository;
import jakarta.transaction.Transactional;
import java.text.SimpleDateFormat;


@Component
@Transactional
@Profile("dev")
public class DataBaseInit implements ApplicationRunner {

    @Autowired private EventRepository eventRepository;
    @Autowired private GalleryRepository galleryRepository;
    @Autowired private UserEntityRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        // 1) Create admin user if not exists
        if (!userRepository.existsByUsername("SanMiguel2025")) {
            String encodedPassword = passwordEncoder.encode("12345");
            UserEntity admin = UserEntity.builder()
                    .username("SanMiguel2025")
                    .password(encodedPassword)
                    .build();
            UserEntity saved = userRepository.save(admin);
            System.out.println("Usuario creado: " + saved.getUsername() + " con ID: " + saved.getUser_id());
        } else {
            System.out.println("El usuario SanMiguel2025 ya existe");
            // Verificar que el usuario existe y puede ser encontrado
            userRepository.findByUsername("SanMiguel2025").ifPresent(user -> {
                System.out.println("Usuario encontrado: " + user.getUsername() + " con ID: " + user.getUser_id());
            });
        }
        
        // Solo inicializar datos si la base de datos está vacía (primera vez)
        if (eventRepository.count() > 0) {
            System.out.println("La base de datos ya contiene datos. No se inicializarán datos de ejemplo.");
            return;
        }

        System.out.println("Inicializando datos de ejemplo...");

        // 2) Events - Solo se crean si no hay eventos
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
        System.out.println("Evento de ejemplo creado: " + event1.getTitle());
        
        // 3) Example gallery seed - Solo se crea si no hay galerías
        if (galleryRepository.count() == 0) {
            Gallery galeria = Gallery.builder()
                .photos(java.util.List.of(
                    "874b1eb2-9ce7-4ba3-a568-9a0b45054482.jpeg",
                    "e9bf1c24-4240-4eae-8e94-3bf0edcfe159.jpeg",
                    "fe4b0e96-3ebd-4cc1-b2f1-7c7c93a75615.jpeg",
                    "71e201c8-838a-4a2b-8a69-e1b040bf9ff0.jpeg",
                    "87b74cbb-595a-4777-b8ec-9169b2a24a12.jpeg",
                    "460a46fc-022d-4a93-aa82-fdbe99c2287f.jpeg",
                    "6d26d4ec-3877-4bd0-acbc-71d6d590f5d6.jpg"
                ))
                .build();
            galleryRepository.save(galeria);
            System.out.println("Galería de ejemplo creada con " + galeria.getPhotos().size() + " fotos");
        }
        
        System.out.println("Inicialización de datos completada.");

    }
}
