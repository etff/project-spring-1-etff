package com.mogaco.project.meet.domain;

import java.time.LocalDate;

public interface MeetSupplier {
    String getLocation();
    String getTitle();
    String getMessage();
    String getTime();
    LocalDate getStartedAt();

}
