package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Member;
import com.example.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> getMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Long memberId, Member member) {
        return memberRepository.findById(memberId)
                .map(existingMember -> {
                    existingMember.setName(member.getName());
                    existingMember.setPosition(member.getPosition());
                    existingMember.setResponsibilities(member.getResponsibilities());
                    existingMember.setPhoto(member.getPhoto());
                    existingMember.setEmail(member.getEmail());
                    existingMember.setPhone(member.getPhone());
                    existingMember.setFoundation(member.getFoundation());
                    return memberRepository.save(existingMember);
                })
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
    }

    @Override
    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new RuntimeException("Member not found with id: " + memberId);
        }
        memberRepository.deleteById(memberId);
    }

    @Override
    public List<Member> searchByPosition(String position) {
        return memberRepository.findByPosition(position);
    }

    @Override
    public Optional<Member> searchByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}