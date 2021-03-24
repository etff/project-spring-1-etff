package com.mogaco.project.meet.domain;

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
public class Meet {

    /**
     * 모임 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
