package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByPosition(String position);
    Optional<Member> findByEmail(String email);
}