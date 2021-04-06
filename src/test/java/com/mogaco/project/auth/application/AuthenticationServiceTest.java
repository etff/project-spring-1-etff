package com.mogaco.project.auth.application;

import com.mogaco.project.auth.infra.JwtUtil;
import com.mogaco.project.global.utils.CustomPasswordEncoder;
import com.mogaco.project.member.domain.Member;
import com.mogaco.project.member.domain.MemberRepository;
import com.mogaco.project.member.domain.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AuthenticationServiceTest {
    private static final String SECRET = "12345678901234567890123456789012";
    private static final long EXPIRED_TIME = 300000;
    private static final String GIVEN_MEMBER_EMAIL = "tester@example.com";
    private static final String GIVEN_MEMBER_PASSWORD = "test";
    private static final String WRONG_EMAIL = GIVEN_MEMBER_EMAIL + "_WRONG";
    private static final String WRONG_PASSWORD = GIVEN_MEMBER_PASSWORD + "_WRONG";
    private static final String ENCODED_PASSWORD = "$2a$10$y9ia8/dUMn1sZz/Qyc/SWuMp0TjaJdoHxnK1lVj3OMkA2WT/31YTq";

    private AuthenticationService authenticationService;
    private MemberRepository memberRepository = mock(MemberRepository.class);
    private RoleRepository roleRepository = mock(RoleRepository.class);

    @BeforeEach
    void setUp() {
        final JwtUtil jwtUtil = new JwtUtil(SECRET, EXPIRED_TIME);
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        authenticationService = new AuthenticationService(jwtUtil,
                memberRepository, roleRepository, new CustomPasswordEncoder(passwordEncoder));
    }

    @Nested
    @DisplayName("login 메서드는")
    class Describe_login {

        @Nested
        @DisplayName("등록된 이메일과 비밀번호가 주어지면")
        class Context_with_exist_email_password {
            final String email = GIVEN_MEMBER_EMAIL;
            final String password = GIVEN_MEMBER_PASSWORD;
            final Member member = Member.builder()
                    .email(GIVEN_MEMBER_EMAIL)
                    .password(ENCODED_PASSWORD)
                    .deleted(false)
                    .build();

            @BeforeEach
            void setUp() {
                given(memberRepository.findByEmail(email))
                        .willReturn(Optional.of(member));
            }

            @DisplayName("인증 토큰을 리턴한다.")
            @Test
            void it_returns_token() {
                String token = authenticationService.login(email, password);
                assertThat(token).contains(".");
            }
        }

        @Nested
        @DisplayName("유효하지 않은 이메일이 주어지면")
        class Context_with_not_exist_email {
            final String email = WRONG_EMAIL;
            final String password = GIVEN_MEMBER_PASSWORD;

            @DisplayName("LoginFailException 예외를 던진다.")
            @Test
            void It_throws_exception() {
                assertThrows(LoginFailException.class,
                        () -> authenticationService.login(email, password));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 비밀번호가 주어지면")
        class Context_with_wrong_password {
            final String email = GIVEN_MEMBER_EMAIL;
            final String password = WRONG_PASSWORD;
            final Member member = Member.builder().id(1L).build();

            @BeforeEach
            void setUp() {
                given(memberRepository.findByEmail(email))
                        .willReturn(Optional.of(member));
            }

            @DisplayName("로그인 실패 예외를 던진다.")
            @Test
            void It_throws_exception() {
                assertThrows(LoginFailException.class,
                        () -> authenticationService.login(email, password));
            }
        }
    }
}
