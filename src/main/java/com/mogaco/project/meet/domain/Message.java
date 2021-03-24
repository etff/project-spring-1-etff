package com.mogaco.project.meet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * 모각코 모집 메시지.
 */
@Embeddable
@NoArgsConstructor
@Getter
public class Message {
    /**
     * 모각코 모집 제목.
     */
    private String title;

    /**
     * 모각코 모집 본문.
     */
    private String message;

    public Message(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        Message message1 = (Message) o;
        return Objects.equals(getTitle(), message1.getTitle()) &&
                Objects.equals(getMessage(), message1.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getMessage());
    }
}
