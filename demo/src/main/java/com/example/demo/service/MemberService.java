package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.MemberDTO;

public interface MemberService {
    List<MemberDTO> getAllMembers();
    Optional<MemberDTO> getMemberById(Long memberId);
    MemberDTO createMember(MemberDTO member);
    MemberDTO updateMember(Long memberId, MemberDTO member);
    void deleteMember(Long memberId);
    List<MemberDTO> searchByPosition(String position);
    Optional<MemberDTO> searchByEmail(String email);
}