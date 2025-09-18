package com.example.demo.controller;

import com.example.demo.model.Administrator;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserEntityRepository;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JWTGenerator;
import com.example.demo.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/administrators")
@CrossOrigin(origins = "http://localhost:4200")
public class AdministratorController {
    // TODO: Verificar funcionamiento del sistema en http://localhost:8080/h2-console y Postman teniendo en cuenta la autorización.
    // TODO: Poner como comentario las urls para mayor facilidad.
    // TODO: Realizar el perfil de producción y probar con una base de datos real (en application.properties está la idea de cómo hacerlo en PostgreSQL, la dependencia ya está en pom.xml).
    // TODO: Realizar validación de tipos de dato y restricciones básicas en todos los controladores. Ejemplo: No se recibe NULL en algunos datos.
    // TODO: Crear DTOs para información de Donors y Administrator (muy importante para seguridad).
    // TODO: Crear error handlers para NotFound, Bad Request, Internal Server Error, etc.
    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTGenerator jwtGenerator;

    // ---------- LOGIN ----------
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Administrator administrator) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(administrator.getUsername(), administrator.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    // ---------- DETAILS ----------
    @GetMapping("/details")
    public ResponseEntity<Administrator> getLoggedAdministrator() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Administrator> administrator = administratorService.searchByUsername(username);

        return administrator.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ---------- CRUD ----------
    @GetMapping
    public List<Administrator> getAll() {
        return administratorService.getAllAdministrators();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrator> getById(@PathVariable Long id) {
        return administratorService.getAdministratorById(id)
                .map(admin -> new ResponseEntity<>(admin, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestBody Administrator administrator,
            @RequestParam("confirm_password") String confirmPassword) {

        if (userRepository.existsByUsername(administrator.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already registered");
        }

        if (!administrator.getPassword().equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        UserEntity userEntity = customUserDetailsService.adminToUser(administrator);
        administrator.setUserEntity(userEntity);

        administratorService.createAdministrator(administrator);
        return ResponseEntity.ok("Administrator created successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Administrator> update(@PathVariable Long id, @RequestBody Administrator updatedAdmin) {
        Administrator editedAdmin = administratorService.updateAdministrator(id, updatedAdmin);
        return ResponseEntity.ok(editedAdmin);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        administratorService.deleteAdministrator(id);
        return ResponseEntity.ok("Administrator deleted successfully");
    }
}