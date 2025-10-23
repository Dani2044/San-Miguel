package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Foundation;
import com.example.demo.model.Member;
import com.example.demo.repository.FoundationRepository;
import com.example.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final FoundationRepository foundationRepository;

    @Override
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream().map(MemberMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<MemberDTO> getMemberById(Long memberId) {
        return memberRepository.findById(memberId).map(MemberMapper::toDTO);
    }

    @Override
    public MemberDTO createMember(MemberDTO memberDto) {
        Member m = Member.builder()
                .name(memberDto.name())
                .position(memberDto.position())
                .responsibilities(memberDto.responsibilities())
                .photo(memberDto.photo())
                .email(memberDto.email())
                .phone(memberDto.phone())
                .build();

        if (memberDto.foundationId() != null) {
            Foundation f = foundationRepository.findById(memberDto.foundationId())
                    .orElseThrow(() -> new RuntimeException("Foundation not found: " + memberDto.foundationId()));
            m.setFoundation(f);
        }

        Member saved = memberRepository.save(m);
        return MemberMapper.toDTO(saved);
    }

    @Override
    public MemberDTO updateMember(Long memberId, MemberDTO memberDto) {
        return memberRepository.findById(memberId)
                .map(existingMember -> {
                    if (memberDto.name() != null) existingMember.setName(memberDto.name());
                    if (memberDto.position() != null) existingMember.setPosition(memberDto.position());
                    if (memberDto.responsibilities() != null) existingMember.setResponsibilities(memberDto.responsibilities());
                    if (memberDto.photo() != null) existingMember.setPhoto(memberDto.photo());
                    if (memberDto.email() != null) existingMember.setEmail(memberDto.email());
                    if (memberDto.phone() != null) existingMember.setPhone(memberDto.phone());
                    if (memberDto.foundationId() != null) {
                        Foundation f = foundationRepository.findById(memberDto.foundationId())
                                .orElseThrow(() -> new RuntimeException("Foundation not found: " + memberDto.foundationId()));
                        existingMember.setFoundation(f);
                    }
                    return MemberMapper.toDTO(memberRepository.save(existingMember));
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
    public List<MemberDTO> searchByPosition(String position) {
        return memberRepository.findByPosition(position).stream().map(MemberMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<MemberDTO> searchByEmail(String email) {
        return memberRepository.findByEmail(email).map(MemberMapper::toDTO);
    }
}