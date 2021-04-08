package com.mogaco.project.meet.application;

import com.mogaco.project.meet.domain.Meet;
import com.mogaco.project.meet.dto.MeetRequestDto;
import com.mogaco.project.meet.infra.MeetRepository;
import com.mogaco.project.member.domain.Member;
import com.mogaco.project.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MeetServiceTest {
    private static final Long GIVEN_MEMBER_ID = 1L;

    private MeetService meetService;
    private MeetRepository meetRepository = mock(MeetRepository.class);
    private MemberRepository memberRepository = mock(MemberRepository.class);

    @BeforeEach
    void setUp() {
        LocationConverter locationConverter = new LocationConverter();
        meetService = new MeetService(meetRepository, locationConverter, memberRepository);
    }

    @Nested
    @DisplayName("createMeeting 메서드는")
    class Describe_createMeeting {

        @Nested
        @DisplayName("모임 생성 명세서가 주어지면")
        class Context_with_meeting_request {
            final MeetRequestDto meetRequestDto = MeetRequestDto.builder()
                    .count(5)
                    .location("seoul/ hongdae")
                    .title("mogaco")
                    .message("study")
                    .time("10:00~14:00")
                    .subject("DevOps")
                    .startedAt(LocalDate.of(2021, 4, 1))
                    .build();
            final Member member = Member.builder()
                    .id(GIVEN_MEMBER_ID).build();

            @BeforeEach
            void setUp() {
                given(meetRepository.save(any(Meet.class))).will(invocation -> {
                    final Meet source = invocation.getArgument(0);
                    return Meet.builder()
                            .id(1L)
                            .location(source.getLocation())
                            .message(source.getMessage())
                            .count(source.getCount())
                            .meetTime(source.getMeetTime())
                            .build();
                });
                given(memberRepository.findById(anyLong()))
                        .willReturn(Optional.of(member));
            }

            @DisplayName("모임 식별자를 리턴한다.")
            @Test
            void it_returns_id() {
                Long meetId = meetService.createMeeting(meetRequestDto, GIVEN_MEMBER_ID);
                assertThat(meetId).isNotNull();
            }
        }
    }
}
