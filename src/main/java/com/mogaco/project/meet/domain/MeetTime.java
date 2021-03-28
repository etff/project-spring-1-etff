package com.mogaco.project.meet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 모임 시간.
 */
@Embeddable
@NoArgsConstructor
@Getter
public class MeetTime {
    /**
     * 시작일.
     */
    private LocalDate startedAt;

    /**
     * 시간.
     */
    private String time;

    public MeetTime(LocalDate startedAt, String time) {
        this.startedAt = startedAt;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeetTime)) {
            return false;
        }
        MeetTime meetTime = (MeetTime) o;
        return Objects.equals(getStartedAt(), meetTime.getStartedAt()) &&
                Objects.equals(getTime(), meetTime.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartedAt(), getTime());
    }
}
