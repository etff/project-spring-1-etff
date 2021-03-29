package com.mogaco.project.global.utils;

import com.mogaco.project.member.domain.MemberSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomPasswordEncoder {
  private final PasswordEncoder passwordEncoder;

  public String getEncodedPassword(MemberSupplier memberSupplier) {
    return passwordEncoder.encode(memberSupplier.getPassword());
  }
}
