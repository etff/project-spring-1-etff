package com.mogaco.project.member.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mogaco.project.auth.application.AuthenticationGuard;
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
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
  private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImlhdCI6MTY0MDk2MjgwMCwiZXh" +
          "wIjoxNjQwOTYzMTAwfQ.2siRnBJmRU2JXjZY0CkQMgnCHRJN4Dld4_wG6R7T-HQ";
  private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
          "eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaD0";

  private static Long GIVEN_ID = 1L;
  private static Long NOT_EXISTED_ID = -1L;
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

  @MockBean(name = "authenticationGuard")
  private AuthenticationGuard authenticationGuard;

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

    given(memberService.registerMember(any(MemberRegisterDto.class))).willReturn(GIVEN_ID);
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
                            .content(objectMapper.writeValueAsString(dto))
                            .header("Authorization", "Bearer " + VALID_TOKEN)
            )

            .andExpect(status().isBadRequest());
  }

  @DisplayName("유효한 회원 정보 갱신 명세서로 회원 갱신")
  @Test
  void updateUserWithValidAttributes() throws Exception {
    given(authenticationGuard.checkIdMatch(any(Authentication.class), anyLong()))
            .willReturn(true);

    mockMvc
            .perform(
                    patch("/api/v1/members/" + GIVEN_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateDto))
                            .header("Authorization", "Bearer " + VALID_TOKEN)
            )
            .andExpect(status().isOk());
  }

  @DisplayName("유효하지 않은 회원 정보 갱신 명세서로 회원 갱신")
  @Test
  void updateUserWithInValidAttributes() throws Exception {
    given(authenticationGuard.checkIdMatch(any(Authentication.class), anyLong()))
            .willReturn(true);

    MemberRegisterDto dto = new MemberRegisterDto();
    mockMvc
            .perform(
                    patch("/api/v1/members/" + GIVEN_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto))
                            .header("Authorization", "Bearer " + VALID_TOKEN)
            )
            .andExpect(status().isBadRequest());
  }

  @DisplayName("등록된 회원 정보 가져오기")
  @Test
  void detailWithExistedMember() throws Exception {
    given(memberService.getMember(GIVEN_ID))
            .willReturn(
                    MemberResponse.builder().id(GIVEN_ID).email(GIVEN_EMAIL).name(GIVEN_NAME).build());
    mockMvc
            .perform(get("/api/v1/members/{id}", 1L)
                    .header("Authorization", "Bearer " + VALID_TOKEN))
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
    given(memberService.getMember(NOT_EXISTED_ID)).willThrow(MemberNotFoundException.class);

    mockMvc.perform(get("/api/v1/members/{id}", NOT_EXISTED_ID)
            .header("Authorization", "Bearer " + VALID_TOKEN)

    ).andExpect(status().isNotFound());
  }

  @DisplayName("등록된 회원 정보를 삭제하기")
  @Test
  void destroyWithExistedId() throws Exception {
    mockMvc.perform(
            delete("/api/v1/members/{id}", GIVEN_ID)
                    .header("Authorization", "Bearer " + VALID_TOKEN)

    ).andExpect(status().isNoContent());

    verify(memberService).deleteMember(GIVEN_ID);
  }

  @DisplayName("등록되지 않은 회원 정보를 삭제하기")
  @Test
  void destroyWithNotExistedId() throws Exception {
    doThrow(new MemberNotFoundException(NOT_EXISTED_ID))
            .when(memberService)
            .deleteMember(NOT_EXISTED_ID);

    mockMvc
            .perform(delete("/api/v1/members/{id}", NOT_EXISTED_ID)
                    .header("Authorization", "Bearer " + VALID_TOKEN)
            )
            .andExpect(status().isNotFound());
  }
}
