package com.mogaco.project.meet.application;

public class MeetingNotFoundException extends RuntimeException {
  public MeetingNotFoundException(Long id) {
    super("Meeting not found: " + id);
  }
}
