package com.mogaco.project.member.dto;

import com.mogaco.project.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 정보 응답.
 */
@Getter
@NoArgsConstructor
public class MemberResponse {
    /**
     * 회원 식별자.
     */
    private Long id;
    /**
     * 회원 이름.
     */
    private String name;
    /**
     * 회원 이메일.
     */
    private String email;

    @Builder
    public MemberResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * 회원 정보를 회원 응답 정보로 생성한다.
     */
    public MemberResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
    }
}
