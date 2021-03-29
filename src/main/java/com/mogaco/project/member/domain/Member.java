package com.mogaco.project.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 회원 정보.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    /**
     * 회원 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 회원 이메일.
     */
    private String email;

    /**
     * 회원 이름.
     */
    private String name;

    /**
     * 회원 비밀번호.
     */
    private String password;

    /**
     * 삭제된 회원이라면 true, 아니면 false.
     */
    private boolean deleted;

    @Builder
    public Member(Long id, String email, String name, String password, boolean deleted) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.deleted = deleted;
    }

    public static Member of(String name, String email) {
        return Member.builder()
                .name(name)
                .email(email)
                .deleted(false)
                .build();
    }

    /**
     * 회원 비밀번호를 암호화하여 저장합니다.
     */
    public void changePassword(String password) {
        this.password = password;
    }

    /**
     * 사용자 정보를 삭제했다고 표시합니다.
     */
    public void destroy() {
        deleted = true;
    }
}
