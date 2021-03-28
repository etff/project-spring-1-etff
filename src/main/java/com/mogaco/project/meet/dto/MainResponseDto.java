package com.mogaco.project.meet.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 메인화면 명세서.
 */
@Getter
@NoArgsConstructor
public class MainResponseDto {
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

    @Builder
    @QueryProjection
    public MainResponseDto(Long meetId, LocalDate startedAt, String title, String location, String time) {
        this.meetId = meetId;
        this.startedAt = startedAt;
        this.title = title;
        this.location = location;
        this.time = time;
    }
}
