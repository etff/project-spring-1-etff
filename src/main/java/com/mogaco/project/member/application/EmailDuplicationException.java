package com.mogaco.project.member.application;

public class EmailDuplicationException extends RuntimeException {
    public EmailDuplicationException(String email) {
        super("email is already existed: " + email);
    }
}
