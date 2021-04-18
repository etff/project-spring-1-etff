package com.mogaco.project.study.domain;

import com.mogaco.project.meet.domain.Meet;
import com.mogaco.project.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

/**
 * 공부 주제.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

    /**
     * 공부 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 공부 주제.
     */
    private String subject;

    @ManyToOne
    @JoinColumn(name = "meet_id")
    private Meet meet;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 스터디 주최 여부.
     */
    @Enumerated(EnumType.STRING)
    private Position position;

    /**
     * 모임 신청 상태.
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Study(Long id, String subject, Meet meet, Position position, Status status, Member member) {
        this.id = id;
        this.subject = subject;
        this.meet = meet;
        this.position = position;
        this.status = status;
        this.member = member;
    }

    public static Study createStudy(String subject, Member member) {
        return Study.builder()
                .subject(subject)
                .position(Position.LEADER)
                .status(Status.APPROVED)
                .member(member)
                .build();
    }

    public static Study joinStudy(String subject, Member member) {
        return Study.builder()
                .subject(subject)
                .position(Position.ATTENDEE)
                .status(Status.APPROVED)
                .member(member)
                .build();
    }

    public void setMeet(Meet meet) {
        this.meet = meet;
    }
}
