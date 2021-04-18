package com.mogaco.project.meet.ui;

import com.mogaco.project.common.BaseControllerTest;
import com.mogaco.project.global.utils.SecurityUtil;
import com.mogaco.project.meet.application.MeetService;
import com.mogaco.project.meet.application.MeetingNotFoundException;
import com.mogaco.project.meet.domain.MeetStatus;
import com.mogaco.project.meet.dto.MainResponseDto;
import com.mogaco.project.meet.dto.MeetDetailResponseDto;
import com.mogaco.project.meet.dto.MeetJoinDto;
import com.mogaco.project.meet.dto.MeetRequestDto;
import com.mogaco.project.meet.dto.MyMeetResponseDto;
import com.mogaco.project.study.domain.Position;
import com.mogaco.project.study.domain.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.JsonFieldType.VARIES;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MeetControllerTest extends BaseControllerTest {
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImlhdCI6MTY0MDk2MjgwMCwiZXh" +
            "wIjoxNjQwOTYzMTAwfQ.2siRnBJmRU2JXjZY0CkQMgnCHRJN4Dld4_wG6R7T-HQ";

    private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaD0";
    public static final String GIVEN_TITLE = "mogaco";
    public static final long GIVEN_ID = 1L;
    public static final long NOT_EXISTED_ID = -1L;
    public static final String GIVEN_LOCATION = "HONGDAE";
    public static final LocalDate GIVEN_START_DAY = LocalDate.of(2021, 4, 1);
    public static final String GIVEN_START_TIME = "10:00~14:00";

    @MockBean
    private MeetService meetService;

    @MockBean
    private SecurityUtil securityUtil;

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
        given(securityUtil.getCurrentMemberId()).willReturn(Optional.of(1L));

        given(meetService.createMeeting(any(MeetRequestDto.class), anyLong()))
                .willReturn(1L);

        PageRequest pageRequest = PageRequest.of(0, 20);
        List<MainResponseDto> result = List.of(MainResponseDto.builder()
                .title(GIVEN_TITLE)
                .meetId(GIVEN_ID)
                .location(GIVEN_LOCATION)
                .startedAt(GIVEN_START_DAY)
                .time(GIVEN_START_TIME)
                .status(MeetStatus.OPEN)
                .build());

        Page<MainResponseDto> page = new PageImpl<>(result, pageRequest, result.size());
        given(meetService.getMeetingsByPageRequest(any(PageRequest.class))).willReturn(page);
    }

    @Test
    void createWithValidAttributes() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.
                        post("/api/v1/meets")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .header("Authorization", "Bearer " + VALID_TOKEN)
        )
                .andExpect(header().string("location", "/meets/1"))
                .andExpect(status().isCreated())
                .andDo(document("create-meet",
                        requestHeaders(headerWithName("Authorization").description("JWT 토큰")),
                        requestFields(
                                fieldWithPath("startedAt").type(VARIES).description("시작일")
                                        .attributes(key("constraints").value("빈 값을 입력할 수 없습니다.")),
                                fieldWithPath("time").type(STRING).description("시간")
                                        .attributes(key("constraints").value("빈 값을 입력할 수 없습니다.")),
                                fieldWithPath("count").type(NUMBER).description("인원")
                                        .attributes(key("constraints").value("0보다 큰 값을 입력해야합니다.")),
                                fieldWithPath("location").type(STRING).description("모임 장소"),
                                fieldWithPath("title").type(STRING).description("제목")
                                        .attributes(key("constraints").value("빈 값을 입력할 수 없습니다.")),
                                fieldWithPath("message").type(STRING).description("본문")
                                        .attributes(key("constraints").value("빈 값을 입력할 수 없습니다.")),
                                fieldWithPath("subject").type(STRING).description("주제").optional()
                        ))
                );

        verify(meetService).createMeeting(any(MeetRequestDto.class), anyLong());
    }

    @Test
    void createWithInvalidAttributes() throws Exception {
        final MeetRequestDto dto = MeetRequestDto.builder()
                .build();

        mockMvc.perform(
                post("/api/v1/meets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", "Bearer " + VALID_TOKEN)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void createWithoutAccessToken() throws Exception {
        mockMvc.perform(
                post("/api/v1/meets")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))

        )
                .andExpect(status().isUnauthorized());
    }


    @Test
    void list() throws Exception {
        mockMvc.perform(
                get("/api/v1/meets")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(GIVEN_TITLE)))
                .andExpect(content().string(containsString(GIVEN_LOCATION)));
    }

    @Test
    void getMeeting() throws Exception {
        MeetDetailResponseDto responseDto = MeetDetailResponseDto.builder()
                .meetId(GIVEN_ID)
                .startedAt(GIVEN_START_DAY)
                .title(GIVEN_TITLE)
                .location(GIVEN_LOCATION)
                .time(GIVEN_START_TIME)
                .studies(new ArrayList())
                .build();

        given(meetService.getMeeting(anyLong()))
                .willReturn(responseDto);

        mockMvc.perform(
                get("/api/v1/meets/{id}", GIVEN_ID)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(GIVEN_TITLE)))
                .andExpect(content().string(containsString(GIVEN_LOCATION)));
    }

    @Test
    void joinMeetingWithValidAttributes() throws Exception {
        final MeetJoinDto meetJoinDto = new MeetJoinDto(GIVEN_ID, "10:00 ~ 14:00", "DDD");

        mockMvc.perform(
                RestDocumentationRequestBuilders.
                        post("/api/v1/meets/{id}/join", GIVEN_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meetJoinDto))
                        .header("Authorization", "Bearer " + VALID_TOKEN)
        )
                .andExpect(header().string("location", "/meets/1"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("join-meeting",
                        requestHeaders(headerWithName("Authorization").description("JWT 토큰")),
                        pathParameters(
                                parameterWithName("id").description("모임 식별자")
                        ),
                        requestFields(
                                attributes(key("meet").value("Fields for meeting join")),
                                fieldWithPath("id").type(NUMBER).description("모임 식별자"),
                                fieldWithPath("time").type(STRING).description("모임 참가시간")
                                        .attributes(key("constraints").value("한 글자 이상 입력해야합니다.")),
                                fieldWithPath("subject").type(STRING).description("참가주제")
                        ))
                );

        verify(meetService).joinMeeting(anyLong(), anyLong(), any(MeetJoinDto.class));
    }

    @Test
    void joinMeetingWithInValidAttributes() throws Exception {
        final MeetJoinDto meetJoinDto = new MeetJoinDto();

        mockMvc.perform(
                post("/api/v1/meets/{id}/join", GIVEN_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meetJoinDto))
                        .header("Authorization", "Bearer " + VALID_TOKEN)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void joinMeetingWithoutAccessToken() throws Exception {
        final MeetJoinDto meetJoinDto = new MeetJoinDto(GIVEN_ID, "10:00 ~ 14:00", "DDD");

        mockMvc.perform(
                post("/api/v1/meets/{id}/join", GIVEN_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meetJoinDto))
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getJoinMeetings() throws Exception {
        final MyMeetResponseDto myMeetResponseDto = MyMeetResponseDto.builder()
                .meetId(GIVEN_ID)
                .title(GIVEN_TITLE)
                .meetStatus(MeetStatus.OPEN)
                .position(Position.LEADER)
                .status(Status.APPROVED)
                .startedAt(GIVEN_START_DAY)
                .build();

        given(meetService.getJoinMeetings(anyLong()))
                .willReturn(Collections.singletonList(myMeetResponseDto));

        mockMvc.perform(
                RestDocumentationRequestBuilders.
                        get("/api/v1/meets/join/{id}", GIVEN_ID)
                        .header("Authorization", "Bearer " + VALID_TOKEN)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(GIVEN_TITLE)))
                .andDo(print())
                .andDo(document("joined-meeting",
                        requestHeaders(headerWithName("Authorization").description("JWT 토큰")),
                        pathParameters(
                                parameterWithName("id").description("회원 식별자")
                        ),
                        responseFields(
                                fieldWithPath("[].meetId").type(NUMBER).description("회원 식별자"),
                                fieldWithPath("[].startedAt").type(VARIES).description("시작일"),
                                fieldWithPath("[].title").type(STRING).description("제목"),
                                fieldWithPath("[].status").type(VARIES).description("신청상태"),
                                fieldWithPath("[].position").type(VARIES).description("참가자 위치"),
                                fieldWithPath("[].meetStatus").type(VARIES).description("모임 개최 여부")
                        ))
                );

        verify(meetService).getJoinMeetings(anyLong());
    }

    @DisplayName("등록된 모임 정보를 삭제하기")
    @Test
    void destroyWithExistedId() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.
                        delete("/api/v1/meets/{id}", GIVEN_ID)
                        .header("Authorization", "Bearer " + VALID_TOKEN)

        ).andExpect(status().isNoContent())
                .andDo(document("delete-meeting",
                        pathParameters(
                                parameterWithName("id").description("모임 식별자")
                        ),
                        requestHeaders(headerWithName("Authorization").description("JWT 토큰"))
                ));

        verify(meetService).deleteMeeting(GIVEN_ID);
    }

    @DisplayName("등록되지 않은 모임 정보를 삭제하기")
    @Test
    void destroyWithNotExistedId() throws Exception {
        doThrow(new MeetingNotFoundException(NOT_EXISTED_ID))
                .when(meetService)
                .deleteMeeting(NOT_EXISTED_ID);

        mockMvc
                .perform(delete("/api/v1/meets/{id}", NOT_EXISTED_ID)
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                )
                .andExpect(status().isNotFound());
    }
}
