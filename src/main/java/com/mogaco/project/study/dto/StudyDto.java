package com.mogaco.project.study.dto;

import com.mogaco.project.member.dto.MemberResponse;
import com.mogaco.project.study.domain.Position;
import com.mogaco.project.study.domain.Status;
import com.mogaco.project.study.domain.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공부 주제 명세서.
 */
@NoArgsConstructor
@Getter
public class StudyDto {
    private Long id;

    /**
     * 공부 주제.
     */
    private String subject;

    /**
     * 회원.
     */
    private MemberResponse member;

    /**
     * 스터디 주최 여부.
     */
    private Position position;

    /**
     * 모임 신청 상태.
     */
    private Status status;

    public StudyDto(Study study) {
        this.id = study.getId();
        this.member = new MemberResponse(study.getMember());
        this.position = study.getPosition();
        this.subject = study.getSubject();
        this.status = study.getStatus();
    }
}
