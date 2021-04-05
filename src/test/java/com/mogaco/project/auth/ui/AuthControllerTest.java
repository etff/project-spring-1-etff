package com.mogaco.project.auth.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mogaco.project.auth.application.AuthenticationService;
import com.mogaco.project.auth.application.LoginFailException;
import com.mogaco.project.auth.dto.AuthRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    private static String GIVEN_EMAIL = "tester@test.com";
    private static String GIVEN_PASSWORD = "password";
    private static String WRONG_EMAIL = GIVEN_EMAIL + "_WRONG";
    private static String WRONG_PASSWORD = GIVEN_PASSWORD + "_WRONG";

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthRequestDto authRequestDto;

    @Test
    void loginWithRightEmailAndPassword() throws Exception {
        authRequestDto = new AuthRequestDto(GIVEN_EMAIL, GIVEN_PASSWORD);
        given(authenticationService.login(GIVEN_EMAIL, GIVEN_PASSWORD))
                .willReturn("a.b.c");

        mockMvc.perform(
                RestDocumentationRequestBuilders.
                        post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequestDto))
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(".")));
    }

    @Test
    void loginWithWrongEmail() throws Exception {
        authRequestDto = new AuthRequestDto(WRONG_EMAIL, GIVEN_PASSWORD);
        given(authenticationService.login(WRONG_EMAIL, GIVEN_PASSWORD))
                .willThrow(new LoginFailException(WRONG_EMAIL));

        mockMvc.perform(
                post("/api/v1/auth")
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
                post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequestDto))
        )
                .andExpect(status().isBadRequest());
    }
}
