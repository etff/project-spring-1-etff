package com.mogaco.project.auth.dto;

import lombok.Getter;

/**
 * 사용자 인증 응답.
 */
@Getter
public class TokenResponseDto {
    /**
     * 사용자 인증 토큰.
     */
    private final String accessToken;

    public TokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
