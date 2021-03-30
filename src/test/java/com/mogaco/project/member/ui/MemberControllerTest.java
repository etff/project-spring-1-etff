package com.mogaco.project.member.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mogaco.project.member.application.MemberService;
import com.mogaco.project.member.dto.MemberRegisterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    private static Long GIVEN_Id = 1L;
    private static String GIVEN_NAME = "user1";
    private static String GIVEN_EMAIL = "test@test.com";
    private static String GIVEN_PASSWORD = "test";

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MemberRegisterDto registerDto;

    @BeforeEach
    void setUp() {
        registerDto =
                MemberRegisterDto.builder()
                        .name(GIVEN_NAME)
                        .email(GIVEN_EMAIL)
                        .password(GIVEN_PASSWORD)
                        .build();

        given(memberService.registerMember(any(MemberRegisterDto.class)))
                .willReturn(GIVEN_Id);
    }

    @DisplayName("유효한 회원 가입 명세서로 회원가입")
    @Test
    void registerMemberWithValidAttributes() throws Exception {
        mockMvc.perform(
                post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated());

        verify(memberService).registerMember(any(MemberRegisterDto.class));
    }

    @DisplayName("유효하지 않은 회원 가입 명세서로 회원가입")
    @Test
    void createWithInvalidAttributes() throws Exception {
        MemberRegisterDto dto = MemberRegisterDto.builder()
                .build();
        mockMvc.perform(
                post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))

        )
                .andExpect(status().isBadRequest());
    }
}
