package com.mogaco.project.study.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    /**
     * 공부 주제.
     */
    private String myStudy;

    @Builder
    public Study(Long id, String myStudy) {
        this.id = id;
        this.myStudy = myStudy;
    }
}
