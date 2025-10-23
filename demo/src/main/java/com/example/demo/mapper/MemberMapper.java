package com.example.demo.mapper;

import com.example.demo.dto.MemberDTO;
import com.example.demo.model.Member;

public final class MemberMapper {
  private MemberMapper() {}

  public static MemberDTO toDTO(Member m) {
    if (m == null) return null;
    Long foundationId = (m.getFoundation() != null) ? m.getFoundation().getFoundationId() : null;
    return new MemberDTO(
        m.getMemberId(),
        m.getName(),
        m.getPosition(),
        m.getResponsibilities(),
        m.getPhoto(),
        m.getEmail(),
        m.getPhone(),
        foundationId
    );
  }
}
