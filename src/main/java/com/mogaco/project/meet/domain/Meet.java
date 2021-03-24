package com.mogaco.project.meet.domain;

import com.mogaco.project.config.BaseEntity;
import com.mogaco.project.study.domain.Location;
import com.mogaco.project.study.domain.Study;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

// TODO : 리더, 이미지 컬럼 추가
/**
 * 모임.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meet extends BaseEntity {

    /**
     * 모임 식별자.
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
     * 참가인원 제한.
     */
    private int count;

    /**
     * 장소.
     */
    @Embedded
    private Location location;

    /**
     * 모임 모집 내용.
     */
    @Embedded
    private Message message;

    /**
     * 공부 주제.
     */
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL )
    @JoinColumn(name = "study_id")
    private Study study;

    @Builder
    public Meet(Long id, LocalDate startedAt, String time, int count, Location location, Message message, Study study) {
        this.id = id;
        this.startedAt = startedAt;
        this.time = time;
        this.count = count;
        this.location = location;
        this.message = message;
        this.study = study;
    }
}
