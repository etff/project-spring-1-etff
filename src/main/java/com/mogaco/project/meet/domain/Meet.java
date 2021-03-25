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
     * 시간.
     */
    @Embedded
    MeetTime meetTime;

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
    public Meet(Long id, MeetTime meetTime, int count, Location location, Message message, List<Study> studies) {
        this.id = id;
        this.meetTime = meetTime;
        this.count = count;
        this.location = location;
        this.message = message;
        this.studies = studies;
    }

    public static Meet of(MeetTime meetTime, int count, Location location, Message message, Study study) {
        final Meet meet = Meet.builder()
                .meetTime(meetTime)
                .count(count)
                .message(message)
                .studies(new ArrayList<>())
                .location(location)
                .build();

        // 최초 생성시, 본인 공부 설정
        meet.addStudy(study);
        return meet;
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
