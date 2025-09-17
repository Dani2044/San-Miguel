package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Administrator;
import com.example.demo.repository.AdministratorRepository;

@Service
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
    public Optional<Administrator> getAdministratorById(Long administratorId) {
        return administratorRepository.findById(administratorId);
    }

    @Override
    public Administrator createAdministrator(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    @Override
    public Administrator updateAdministrator(Long administratorId, Administrator administrator) {
        return administratorRepository.findById(administratorId)
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
                .orElseThrow(() -> new RuntimeException("Administrator not found with id: " + administratorId));
    }

    @Override
    public void deleteAdministrator(Long administratorId) {
        if (!administratorRepository.existsById(administratorId)) {
            throw new RuntimeException("Administrator not found with id: " + administratorId);
        }
        administratorRepository.deleteById(administratorId);
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