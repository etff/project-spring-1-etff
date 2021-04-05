package com.mogaco.project.meet.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mogaco.project.meet.application.MeetService;
import com.mogaco.project.meet.dto.MeetRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MeetControllerTest {
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6MSwiaWF0IjoxNjE3N" +
            "jA0OTM3LCJleHAiOjE2MTc2MTU3Mzd9.ddcbDNF2ZCf5RWV3ApI0uRyxsNXQRVZlMs3kB7Kmybg";

    private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaD0";

    @MockBean
    private MeetService meetService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private MeetRequestDto requestDto;

    @BeforeEach
    void setUp() {
        requestDto = MeetRequestDto.builder()
                .count(5)
                .location("seoul/ hongdae")
                .title("mogaco")
                .message("study")
                .time("10:00~14:00")
                .subject("DevOps")
                .startedAt(LocalDate.of(2021, 4, 1))
                .build();

        given(meetService.createMeeting(any(MeetRequestDto.class)))
                .willReturn(1L);
    }

    @Test
    void createWithValidAttributes() throws Exception {
        mockMvc.perform(
                post("/api/v1/meets")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto))
                        .header("Authorization", "Bearer " + VALID_TOKEN)
        )
                .andExpect(header().string("location", "/meets/1"))
                .andExpect(status().isCreated());

        verify(meetService).createMeeting(any(MeetRequestDto.class));
    }

    @Test
    void createWithInvalidAttributes() throws Exception {
        final MeetRequestDto dto = MeetRequestDto.builder()
                .build();

        mockMvc.perform(
                post("/api/v1/meets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto))
                        .header("Authorization", "Bearer " + VALID_TOKEN)
        )
                .andExpect(status().isBadRequest());
    }
}
