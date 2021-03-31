package com.mogaco.project.member.application;

import com.mogaco.project.global.utils.CustomPasswordEncoder;
import com.mogaco.project.member.domain.Member;
import com.mogaco.project.member.domain.MemberRepository;
import com.mogaco.project.member.dto.MemberRegisterDto;
import com.mogaco.project.member.dto.MemberResponse;
import com.mogaco.project.member.dto.MemberUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MemberServiceTest {
  private static Long GIVEN_ID = 1L;
  private static Long NOT_EXISTED_ID = -1L;
  private static String GIVEN_NAME = "user1";
  private static String GIVEN_EMAIL = "test@test.com";
  private static String GIVEN_PASSWORD = "test";
  private static String UPDATE_NAME = GIVEN_NAME + "_UPDATED";
  private static String UPDATE_EMAIL = GIVEN_EMAIL + "_UPDATED";

  private CustomPasswordEncoder passwordEncoder = mock(CustomPasswordEncoder.class);

  private MemberService memberService;

  private MemberRepository memberRepository = mock(MemberRepository.class);

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

  @Nested
  @DisplayName("getMember 메서드는")
  class Describe_getMember {

    @Nested
    @DisplayName("등록된 회원 id가 주어지면")
    class Context_with_member_id {
      final Long memberId = GIVEN_ID;
      final MemberResponse memberResponse =
              MemberResponse.builder().id(GIVEN_ID).name(GIVEN_NAME).email(GIVEN_EMAIL).build();

      @BeforeEach
      void setUp() {
        given(memberRepository.findByIdAndDeletedIsFalse(memberId))
                .willReturn(
                        Optional.of(
                                Member.builder().id(GIVEN_ID).name(GIVEN_NAME).email(GIVEN_EMAIL).build()));
      }

      @DisplayName("회원 정보를 리턴한다.")
      @Test
      void it_returns_member_response() {
        assertThat(memberService.getMember(memberId)).isEqualTo(memberResponse);
      }
    }

    @Nested
    @DisplayName("존재하지 않은 회원 id가 주어지면")
    class Context_with_not_exist_member {
      final Long memberId = NOT_EXISTED_ID;

      @DisplayName("MemberNotFoundException 예외를 던진다.")
      @Test
      void it_throws_member_not_found_exception() {
        assertThrows(MemberNotFoundException.class, () -> memberService.getMember(memberId));
      }
    }
  }

  @Nested
  @DisplayName("deleteMember 메서드는")
  class Describe_deleteMember {

    @Nested
    @DisplayName("등록된 회원 id가 주어지면")
    class Context_with_member_id {
      final Long memberId = GIVEN_ID;
      final Member member =
              Member.builder().id(GIVEN_ID).name(GIVEN_NAME).email(GIVEN_EMAIL).deleted(false).build();

      @BeforeEach
      void setUp() {
        given(memberRepository.findByIdAndDeletedIsFalse(memberId)).willReturn(Optional.of(member));
      }

      @DisplayName("회원 삭제로 표기한다 ")
      @Test
      void it_change_member_deleted() {
        memberService.deleteMember(memberId);
        assertThat(member.isDeleted()).isTrue();
      }
    }

    @Nested
    @DisplayName("존재하지 않은 회원 id가 주어지면")
    class Context_with_not_exist_member {
      final Long memberId = NOT_EXISTED_ID;

      @DisplayName("MemberNotFoundException 예외를 던진다.")
      @Test
      void it_throws_member_not_found_exception() {
        assertThrows(MemberNotFoundException.class, () -> memberService.deleteMember(memberId));
      }
    }
  }
}
