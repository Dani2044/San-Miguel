package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Foundation;

@Repository
public interface FoundationRepository extends JpaRepository<Foundation, Long> { 
}