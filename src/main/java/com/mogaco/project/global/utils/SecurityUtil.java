package com.mogaco.project.global.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtil {
  /**
   * 현재 접속한 회원 아이디를 리턴합니다.
   *
   * @return 접속한 회원 아이디
   */
  public Optional<Long> getCurrentMemberId() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      return Optional.empty();
    }
    return Optional.ofNullable((Long) authentication.getPrincipal());
  }
}
