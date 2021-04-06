package com.mogaco.project.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


/**
 * 사용자 인증 요청.
 */
@Getter
@NoArgsConstructor
public class AuthRequestDto {
    /**
     * 이메일.
     */
    @Email
    private String email;
    /**
     * 비밀번호.
     */
    @NotBlank
    private String password;

    public AuthRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
