package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Administrator;

public interface AdministratorService {
    // TODO: Realizar validaciones de reglas del negocio en todos los servicios. Ejemplo: La cantidad donada no es negativa.
    List<Administrator> getAllAdministrators();
    Optional<Administrator> getAdministratorById(Long administratorId);
    Administrator createAdministrator(Administrator administrator);
    Administrator updateAdministrator(Long administratorId, Administrator administrator);
    void deleteAdministrator(Long administratorId);
    Optional<Administrator> searchByUsername(String username);
    Optional<Administrator> searchByEmail(String email);    
}