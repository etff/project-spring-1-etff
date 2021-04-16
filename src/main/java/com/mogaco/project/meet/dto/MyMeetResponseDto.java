package com.mogaco.project.meet.dto;

import com.mogaco.project.meet.domain.Meet;
import com.mogaco.project.meet.domain.MeetStatus;
import com.mogaco.project.study.domain.Position;
import com.mogaco.project.study.domain.Status;
import com.mogaco.project.study.domain.Study;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 나의 모임 명세서.
 */
@Getter
@NoArgsConstructor
public class MyMeetResponseDto {
    /**
     * 모임 식별자.
     */
    private Long meetId;
    /**
     * 시작일.
     */
    private LocalDate startedAt;
    /**
     * 제목.
     */
    private String title;

    /**
     * 신청 상태.
     */
    private Status status;

    /**
     * 스터디 주최.
     */
    private Position position;

    /**
     * 모임 상태.
     */
    private MeetStatus meetStatus;

    @Builder
    public MyMeetResponseDto(Long meetId, LocalDate startedAt, String title, Status status, Position position, MeetStatus meetStatus) {
        this.meetId = meetId;
        this.startedAt = startedAt;
        this.title = title;
        this.status = status;
        this.position = position;
        this.meetStatus = meetStatus;
    }

    public MyMeetResponseDto(Study study) {
        final Meet meet = study.getMeet();
        this.meetId = meet.getId();
        this.startedAt = meet.getMeetTime().getStartedAt();
        this.title = meet.getMessage().getTitle();
        this.status = study.getStatus();
        this.position = study.getPosition();
        this.meetStatus = meet.getMeetStatus();
    }
}
