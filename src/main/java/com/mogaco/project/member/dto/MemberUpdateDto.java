package com.mogaco.project.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 회원 수정 명세서.
 */
@NoArgsConstructor
@Getter
public class MemberUpdateDto {

    @NotBlank
    private String name;

    @Email
    private String email;

    public MemberUpdateDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
