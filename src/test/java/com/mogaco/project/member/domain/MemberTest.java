package com.mogaco.project.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
  private static final String GIVEN_NAME = "user1";
  private static final String GIVEN_EMAIL = "test@test.com";
  private static final String GIVEN_PASSWORD = "password";
  private Member member;

  @BeforeEach
  void setUp() {
    member =
            Member.builder()
                    .name(GIVEN_NAME)
                    .email(GIVEN_EMAIL)
                    .password(GIVEN_PASSWORD)
                    .deleted(false)
                    .build();
  }

  @DisplayName("회원 생성하고, 생성자 값을 리턴받을 수 있다")
  @Test
  void createMember() {
    assertThat(member.getName()).isEqualTo(GIVEN_NAME);
    assertThat(member.getEmail()).isEqualTo(GIVEN_EMAIL);
  }

  @DisplayName("회원을 삭제 상태로 표기한다")
  @Test
  void destroyMember() {
    // when
    member.destroy();

    // then
    assertThat(member.isDeleted()).isTrue();
  }
}
