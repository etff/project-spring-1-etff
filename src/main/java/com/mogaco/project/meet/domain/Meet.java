package com.mogaco.project.meet.domain;

import com.mogaco.project.global.config.BaseEntity;
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
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 시작일.
     */
    private LocalDate startedAt;

    /**
     * 시간.
     */
    private String time;

    /**
     * 인원.
     */
    private int count;

    /**
     * 장소.
     */
    @Embedded
    private Location location;

    /**
     * 메시지.
     */
    @Embedded
    private Message message;

    /**
     * 공부 주제.
     */
    @OneToMany(mappedBy = "meet", cascade = CascadeType.ALL)
    private List<Study> studies = new ArrayList<>();

    @Builder
    public Meet(Long id, LocalDate startedAt, String time, int count, Location location, Message message, List<Study> studies) {
        this.id = id;
        this.startedAt = startedAt;
        this.time = time;
        this.count = count;
        this.location = location;
        this.message = message;
        this.studies = studies;
    }

    /**
     * 모임 참가하기.
     * @param study 공부
     */
    public void addStudy(Study study) {
        studies.add(study);
        study.setMeet(this);
    }
}
