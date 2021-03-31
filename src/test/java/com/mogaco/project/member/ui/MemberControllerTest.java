package com.mogaco.project.member.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mogaco.project.member.application.MemberNotFoundException;
import com.mogaco.project.member.application.MemberService;
import com.mogaco.project.member.dto.MemberRegisterDto;
import com.mogaco.project.member.dto.MemberResponse;
import com.mogaco.project.member.dto.MemberUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    private static Long GIVEN_Id = 1L;
    private static Long NOT_FOUND_ID = -1L;
    private static String GIVEN_NAME = "user1";
    private static String GIVEN_EMAIL = "test@test.com";
    private static String GIVEN_PASSWORD = "test";
    private static String UPDATE_NAME = GIVEN_NAME + "_UPDATED";
    private static String UPDATE_EMAIL = GIVEN_EMAIL + "_UPDATED";

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MemberRegisterDto registerDto;

    private MemberUpdateDto updateDto;

    @BeforeEach
    void setUp() {
        registerDto =
                MemberRegisterDto.builder()
                        .name(GIVEN_NAME)
                        .email(GIVEN_EMAIL)
                        .password(GIVEN_PASSWORD)
                        .build();

        updateDto = new MemberUpdateDto(UPDATE_NAME, UPDATE_EMAIL);

        given(memberService.registerMember(any(MemberRegisterDto.class))).willReturn(GIVEN_Id);
    }

    @DisplayName("유효한 회원 가입 명세서로 회원가입")
    @Test
    void registerMemberWithValidAttributes() throws Exception {
        mockMvc
                .perform(
                        post("/api/v1/members")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(content().string("1"));
        verify(memberService).registerMember(any(MemberRegisterDto.class));
    }

    @DisplayName("유효하지 않은 회원 가입 명세서로 회원가입")
    @Test
    void createWithInvalidAttributes() throws Exception {
        MemberRegisterDto dto = MemberRegisterDto.builder().build();
        mockMvc
                .perform(
                        post("/api/v1/members")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("유효한 회원 정보 갱신 명세서로 회원 갱신")
    @Test
    void updateUserWithValidAttributes() throws Exception {
        mockMvc
                .perform(
                        patch("/api/v1/members/" + GIVEN_Id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk());
    }

    @DisplayName("유효하지 않은 회원 정보 갱신 명세서로 회원 갱신")
    @Test
    void updateUserWithInValidAttributes() throws Exception {
        MemberRegisterDto dto = new MemberRegisterDto();
        mockMvc
                .perform(
                        patch("/api/v1/members/" + GIVEN_Id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("등록된 회원 정보 가져오기")
    @Test
    void detailWithExistedMember() throws Exception {
        given(memberService.getMember(GIVEN_Id))
                .willReturn(
                        MemberResponse.builder().id(GIVEN_Id).email(GIVEN_EMAIL).name(GIVEN_NAME).build());
        mockMvc
                .perform(get("/api/v1/members/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(GIVEN_NAME)))
                .andExpect(content().string(containsString(GIVEN_EMAIL)))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(content().string(containsString(GIVEN_NAME)));
    }

    @DisplayName("등록되지 않은 회원 정보 가져오기")
    @Test
    void detailWithNotExistedMember() throws Exception {
        given(memberService.getMember(NOT_FOUND_ID))
                .willThrow(MemberNotFoundException.class);

        mockMvc.perform(get("/api/v1/members/{id}", NOT_FOUND_ID))
                .andExpect(status().isNotFound());
    }
}
