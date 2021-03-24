package com.mogaco.project.study.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("스터디 클래스")
class StudyTest {

    private static final String MY_STUDY = "SPRING";


    @DisplayName("스터디를 생성하고, 생성자 값을 리턴받을 수 있다")
    @Test
    void create() {

        Study study = Study.builder()
                .myStudy(MY_STUDY)
                .build();
        assertThat(study.getMyStudy()).isEqualTo(MY_STUDY);
    }
}
