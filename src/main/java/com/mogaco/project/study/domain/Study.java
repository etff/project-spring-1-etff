package com.mogaco.project.study.domain;

import com.mogaco.project.meet.domain.Meet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

// TODO: 담당자(member) 추가
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
    private String myStudy;

    @ManyToOne
    @JoinColumn(name = "meet_id")
    private Meet meet;

    @Builder
    public Study(Long id, String myStudy, Meet meet) {
        this.id = id;
        this.myStudy = myStudy;
        this.meet = meet;
    }

    public void setMeet(Meet meet) {
        this.meet = meet;
    }
}
