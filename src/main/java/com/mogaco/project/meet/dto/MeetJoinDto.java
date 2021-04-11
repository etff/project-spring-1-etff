package com.mogaco.project.meet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 모임 참가 명세서.
 */
@Getter
@NoArgsConstructor
public class MeetJoinDto {
    /**
     * 모임 식별자.
     */
    private Long id;
    /**
     * 모임 참가시간.
     */
    @NotBlank
    private String time;

    /**
     * 참가 주제.
     */
    private String subject;

    public MeetJoinDto(Long id, String time, String subject) {
        this.id = id;
        this.time = time;
        this.subject = subject;
    }
}
