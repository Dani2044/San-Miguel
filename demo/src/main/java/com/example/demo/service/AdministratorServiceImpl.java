package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Administrator;
import com.example.demo.repository.AdministratorRepository;

public class AdministratorServiceImpl implements AdministratorService {
    
    private final AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public List<Administrator> getAllAdministrators() {
        return administratorRepository.findAll();
    }

    @Override
    public Optional<Administrator> getAdministratorById(Long administrator_id) {
        return administratorRepository.findById(administrator_id);
    }

    @Override
    public Administrator createAdministrator(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    @Override
    public Administrator updateAdministrator(Long administrator_id, Administrator administrator) {
        return administratorRepository.findById(administrator_id)
                .map(existingAdmin -> {
                    existingAdmin.setUsername(administrator.getUsername());
                    existingAdmin.setPassword(administrator.getPassword());
                    existingAdmin.setName(administrator.getName());
                    existingAdmin.setEmail(administrator.getEmail());
                    existingAdmin.setPhoto(administrator.getPhoto());
                    existingAdmin.setPhone(administrator.getPhone());
                    existingAdmin.setFoundation(administrator.getFoundation());
                    return administratorRepository.save(existingAdmin);
                })
                .orElseThrow(() -> new RuntimeException("Administrator not found with id: " + administrator_id));
    }

    @Override
    public void deleteAdministrator(Long administrator_id) {
        if (!administratorRepository.existsById(administrator_id)) {
            throw new RuntimeException("Administrator not found with id: " + administrator_id);
        }
        administratorRepository.deleteById(administrator_id);
    }

    @Override
    public Optional<Administrator> searchByUsername(String username) {
        return administratorRepository.findByUsername(username);
    }

    @Override
    public Optional<Administrator> searchByEmail(String email) {
        return administratorRepository.findByEmail(email);
    }
}