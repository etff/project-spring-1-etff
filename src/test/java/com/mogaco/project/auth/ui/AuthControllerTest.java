package com.mogaco.project.auth.ui;

import com.mogaco.project.auth.application.AuthenticationService;
import com.mogaco.project.auth.application.LoginFailException;
import com.mogaco.project.auth.dto.AuthRequestDto;
import com.mogaco.project.common.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends BaseControllerTest {
    private static String GIVEN_EMAIL = "tester@test.com";
    private static String GIVEN_PASSWORD = "password";
    private static String WRONG_EMAIL = GIVEN_EMAIL + "_WRONG";
    private static String WRONG_PASSWORD = GIVEN_PASSWORD + "_WRONG";

    @MockBean
    private AuthenticationService authenticationService;

    private AuthRequestDto authRequestDto;

    @Test
    void loginWithRightEmailAndPassword() throws Exception {
        authRequestDto = new AuthRequestDto(GIVEN_EMAIL, GIVEN_PASSWORD);
        given(authenticationService.login(GIVEN_EMAIL, GIVEN_PASSWORD))
                .willReturn("a.b.c");

        mockMvc.perform(
                RestDocumentationRequestBuilders.
                        post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequestDto))
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(".")))
                .andDo(print())
                .andDo(document("login",
                        requestFields(
                                fieldWithPath("email").type(STRING).description("이메일").optional(),
                                fieldWithPath("password").type(STRING).description("비밀번호").optional()
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(STRING).description("응답 토큰")
                        ))
                );
    }

    @Test
    void loginWithWrongEmail() throws Exception {
        authRequestDto = new AuthRequestDto(WRONG_EMAIL, GIVEN_PASSWORD);
        given(authenticationService.login(WRONG_EMAIL, GIVEN_PASSWORD))
                .willThrow(new LoginFailException(WRONG_EMAIL));

        mockMvc.perform(
                post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequestDto))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginWithWrongPassword() throws Exception {
        authRequestDto = new AuthRequestDto(GIVEN_EMAIL, WRONG_PASSWORD);
        given(authenticationService.login(GIVEN_EMAIL, WRONG_PASSWORD))
                .willThrow(new LoginFailException(GIVEN_EMAIL));

        mockMvc.perform(
                post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequestDto))
        )
                .andExpect(status().isBadRequest());
    }
}
