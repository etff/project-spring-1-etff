package com.mogaco.project.member.application;

import com.mogaco.project.global.utils.CustomPasswordEncoder;
import com.mogaco.project.member.domain.Member;
import com.mogaco.project.member.domain.MemberRepository;
import com.mogaco.project.member.dto.MemberRegisterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
  private static String GIVEN_NAME = "user1";
  private static String GIVEN_EMAIL = "test@test.com";
  private static String GIVEN_PASSWORD = "test";

  @Mock
  private CustomPasswordEncoder passwordEncoder;

  private MemberService memberService;

  @Mock
  private MemberRepository memberRepository;

  @BeforeEach
  void setUp() {
    memberService = new MemberService(memberRepository, passwordEncoder);
  }

  @Nested
  @DisplayName("registerMember 메서드는")
  class Describe_registerMember {

    @Nested
    @DisplayName("회원 가입 명세서가 주어지면")
    class Context_with_member_request {
      final MemberRegisterDto registerDto =
              MemberRegisterDto.builder()
                      .name(GIVEN_NAME)
                      .email(GIVEN_EMAIL)
                      .password(GIVEN_PASSWORD)
                      .build();

      @BeforeEach
      void setUp() {
        given(memberRepository.save(any(Member.class)))
                .will(
                        invocation -> {
                          final Member source = invocation.getArgument(0);
                          return Member.builder()
                                  .id(1L)
                                  .email(source.getEmail())
                                  .name(source.getName())
                                  .build();
                        });
      }

      @DisplayName("회원 식별자를 리턴한다.")
      @Test
      void it_returns_member_id() {
        Long meetId = memberService.registerMember(registerDto);
        assertThat(meetId).isNotNull();
      }
    }
  }
}
