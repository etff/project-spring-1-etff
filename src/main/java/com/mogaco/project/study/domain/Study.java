package com.mogaco.project.study.domain;

import com.mogaco.project.config.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;
import java.time.LocalDate;

/**
 * 스터디 정보.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study extends BaseEntity {

    /**
     * 스터디 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 스터디 일자.
     */
    private LocalDate startedAt;

    /**
     * 스터디 시간.
     */
    private String time;

    /**
     * 참가인원.
     */
    private int count;

    /**
     * 장소.
     */
    @Embedded
    private Location location;

    /**
     * 제목.
     */
    private String title;

    /**
     * 메시지.
     */
    @Lob
    private String message;

    /**
     * 나의 주제.
     */
    private String myStudy;

    @Builder
    public Study(Long id, LocalDate startedAt, String time, int count, Location location, String title, String message, String myStudy) {
        this.id = id;
        this.startedAt = startedAt;
        this.time = time;
        this.count = count;
        this.location = location;
        this.title = title;
        this.message = message;
        this.myStudy = myStudy;
    }
}
