package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Administrator;

public interface AdministratorService {
    List<Administrator> getAllAdministrators();
    Optional<Administrator> getAdministratorById(Long administrator_id);
    Administrator createAdministrator(Administrator administrator);
    Administrator updateAdministrator(Long administrator_id, Administrator administrator);
    void deleteAdministrator(Long administrator_id);
    Optional<Administrator> searchByUsername(String username);
    Optional<Administrator> searchByEmail(String email);    
}