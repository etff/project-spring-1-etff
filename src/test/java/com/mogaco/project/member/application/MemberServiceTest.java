package com.mogaco.project.member.application;

import com.mogaco.project.global.utils.CustomPasswordEncoder;
import com.mogaco.project.member.domain.Member;
import com.mogaco.project.member.domain.MemberRepository;
import com.mogaco.project.member.dto.MemberRegisterDto;
import com.mogaco.project.member.dto.MemberUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
  private static Long GIVEN_ID = 1L;
  private static String GIVEN_NAME = "user1";
  private static String GIVEN_EMAIL = "test@test.com";
  private static String GIVEN_PASSWORD = "test";
  private static String UPDATE_NAME = GIVEN_NAME + "_UPDATED";
  private static String UPDATE_EMAIL = GIVEN_EMAIL + "_UPDATED";

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
    class Context_with_member_register_request {
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
                                  .id(GIVEN_ID)
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

  @Nested
  @DisplayName("updateMember 메서드는")
  class Describe_updateMember {

    @Nested
    @DisplayName("유효한 회원의 갱신 명세서가 주어지면")
    class Context_with_member_update_request {
      final MemberUpdateDto updateDto = new MemberUpdateDto(UPDATE_NAME, UPDATE_NAME);

      @BeforeEach
      void setUp() {
        given(memberRepository.findByIdAndDeletedIsFalse(GIVEN_ID))
                .willReturn(Optional.of(Member.builder().name(GIVEN_NAME).email(GIVEN_EMAIL).build()));
      }

      @DisplayName("예외를 던지지 않는다.")
      @Test
      void it_does_not_throws_exception() {
        assertDoesNotThrow(() -> memberService.updateMember(GIVEN_ID, updateDto));
      }
    }

    @Nested
    @DisplayName("존재하지 않은 회원의 갱신 명세서가 주어지면")
    class Context_with_member_not_exist_member {
      final MemberUpdateDto updateDto = new MemberUpdateDto();

      @DisplayName("MemberNotFoundException 예외를 던진다.")
      @Test
      void it_throws_member_not_found_exception() {
        assertThrows(
                MemberNotFoundException.class, () -> memberService.updateMember(GIVEN_ID, updateDto));
      }
    }
  }
}
