package com.mogaco.project.meet.dto;

import com.mogaco.project.meet.domain.Meet;
import com.mogaco.project.study.dto.StudyDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 모임 상세 명세서.
 */
@NoArgsConstructor
@Getter
public class MeetDetailResponseDto {
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
     * 장소.
     */
    private String location;
    /**
     * 시간.
     */
    private String time;

    /**
     * 공부 주제.
     */
    List<StudyDto> studies = new ArrayList<>();

    @Builder
    public MeetDetailResponseDto(Long meetId, LocalDate startedAt, String title, String location, String time, List studies) {
        this.meetId = meetId;
        this.startedAt = startedAt;
        this.title = title;
        this.location = location;
        this.time = time;
        this.studies = studies;
    }

    public MeetDetailResponseDto(Meet meet) {
        this.meetId = meet.getId();
        this.startedAt = meet.getMeetTime().getStartedAt();
        this.title = meet.getMessage().getTitle();
        this.location = meet.getLocation().getLocation();
        this.time = meet.getMeetTime().getTime();
        this.studies = meet.getStudies()
                .stream()
                .map(StudyDto::new)
                .collect(Collectors.toUnmodifiableList());
    }
}
