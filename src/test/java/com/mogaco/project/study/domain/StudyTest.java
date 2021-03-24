package com.mogaco.project.study.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("스터디 클래스")
class StudyTest {
    private static final String TIME = "14:00 ~ 16:00";
    private static final String TITLE = "let's study";
    private static final String MESSAGE = "share with me";
    private static final String MY_STUDY = "SPRING";
    private static final LocalDate STARTED_AT = LocalDate.of(2021, 4, 10);

    @DisplayName("스터디를 생성하고, 생성자 값을 리턴받을 수 있다")
    @Test
    void create() {
        final Location location = new Location("seoul", "hongdae");

        Study study = Study.builder()
                .startedAt(STARTED_AT)
                .time(TIME)
                .title(TITLE)
                .location(location)
                .message(MESSAGE)
                .myStudy(MY_STUDY)
                .build();

        assertThat(study.getStartedAt()).isEqualTo(STARTED_AT);
        assertThat(study.getTime()).isEqualTo(TIME);
        assertThat(study.getTitle()).isEqualTo(TITLE);
        assertThat(study.getLocation()).isEqualTo(location);
        assertThat(study.getMessage()).isEqualTo(MESSAGE);
        assertThat(study.getMyStudy()).isEqualTo(MY_STUDY);
    }
}
