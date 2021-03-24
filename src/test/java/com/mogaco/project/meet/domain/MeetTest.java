package com.mogaco.project.meet.domain;

import com.mogaco.project.study.domain.Location;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MeetTest {
    private static final String TIME = "14:00 ~ 16:00";
    private static final String TITLE = "let's study";
    private static final String MESSAGE = "share with me";
    private static final LocalDate STARTED_AT = LocalDate.of(2021, 4, 10);
    private static final int COUNT = 3;

    @Test
    void create() {
        final Location location = new Location("seoul", "hongdae");
        final Message message = new Message(TITLE, MESSAGE);

        Meet meet = Meet.builder()
                .location(location)
                .message(message)
                .count(COUNT)
                .startedAt(STARTED_AT)
                .time(TIME)
                .build();

        assertThat(meet.getCount()).isEqualTo(COUNT);
        assertThat(meet.getLocation()).isEqualTo(location);
        assertThat(meet.getCount()).isEqualTo(COUNT);
        assertThat(meet.getCount()).isEqualTo(COUNT);
    }
}
