package com.mogaco.project.member.application;

public class MemberNotFoundException extends RuntimeException {
  public MemberNotFoundException(Long id) {
    super("Member not found: " + id);
  }
}
