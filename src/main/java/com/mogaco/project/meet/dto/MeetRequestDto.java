package com.mogaco.project.meet.dto;

import com.mogaco.project.meet.domain.MeetSupplier;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * 모임 생성 명세서.
 */
@Getter
@NoArgsConstructor
public class MeetRequestDto implements MeetSupplier {
    /**
     * 시작일.
     */
    @NonNull
    private LocalDate startedAt;

    /**
     * 시간.
     */
    @NotBlank
    private String time;

    /**
     * 인원.
     */
    @Positive
    private int count;

    /**
     * 장소.
     */
    @NotBlank
    private String location;

    /**
     * 제목.
     */
    @NotBlank
    private String title;

    /**
     * 메시지.
     */
    @NotBlank
    private String message;

    /**
     * 주제.
     */
    private String subject;

    @Builder
    public MeetRequestDto(
            LocalDate startedAt, String time, int count, String location, String title, String message, String subject) {
        this.startedAt = startedAt;
        this.time = time;
        this.count = count;
        this.location = location;
        this.title = title;
        this.message = message;
        this.subject = subject;
    }
}
