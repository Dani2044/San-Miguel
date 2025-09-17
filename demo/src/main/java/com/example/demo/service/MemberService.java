package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Member;

public interface MemberService {
    List<Member> getAllMembers();
    Optional<Member> getMemberById(Long memberId);
    Member createMember(Member member);
    Member updateMember(Long memberId, Member member);
    void deleteMember(Long memberId);
    List<Member> searchByPosition(String position);
    Optional<Member> searchByEmail(String email);
}