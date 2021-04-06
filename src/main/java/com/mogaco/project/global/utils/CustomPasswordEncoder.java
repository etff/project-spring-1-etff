package com.mogaco.project.global.utils;

import com.mogaco.project.member.domain.Member;
import com.mogaco.project.member.domain.MemberSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomPasswordEncoder {
  private final PasswordEncoder passwordEncoder;

  /**
   * 사용자 비밀번호를 암호화합니다.
   */
  public String getEncodedPassword(MemberSupplier memberSupplier) {
    return passwordEncoder.encode(memberSupplier.getPassword());
  }

  /**
   * 인증정보로 받은 비밀번호가 저장된 비밀번호가 일치하면 true, 일치하지 않으면 false를 리턴한다.
   *
   * @param password    입력받은 비밀번호
   * @param savedMember 저장된 회원정보
   * @return 비밀번호 동일 여부
   */
  public boolean isPasswordMatch(String password, Member savedMember) {
    return passwordEncoder.matches(password, savedMember.getPassword());
  }
}
