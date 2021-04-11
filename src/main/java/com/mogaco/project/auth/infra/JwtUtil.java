package com.mogaco.project.auth.infra;

import com.mogaco.project.auth.application.InvalidTokenException;
import com.mogaco.project.global.utils.TimeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * JWT 토큰을 관리합니다.
 */
@Component
public class JwtUtil {

  private final Key key;

  private long validityInMilliseconds;

  private LocalDateTime time;

  public JwtUtil(
          @Value("${jwt.secret}") String secret,
          @Value("${jwt.expire-length}") long validityInMilliseconds) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
    this.validityInMilliseconds = validityInMilliseconds;
    this.time = LocalDateTime.now();
  }

  /**
   * 토큰을 생성합니다.
   *
   * @param userId 사용자 id
   * @return 사용자 JWT 토큰
   */
  public String createToken(Long userId) {
    Date now = TimeUtil.convertLocalDateTime(time);
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
            .claim("memberId", userId)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key)
            .compact();
  }

  /**
   * 토큰을 복호화합니다.
   *
   * @param token JWT 토큰
   * @return 사용자 정보
   */
  public Claims decode(String token) {
    if (token == null || token.isBlank()) {
      throw new InvalidTokenException(token);
    }

    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    } catch (SignatureException e) {
      throw new InvalidTokenException(token, e);
    }
  }

  /**
   * 유효한 인증 토큰이면 true, 그렇지 않다면 false를 리턴합니다.
   *
   * @param token 인증 토큰
   * @return 인증 성공 여부
   */
  public boolean validateToken(String token) {
    Claims claims = this.decode(token);
    return !claims.getExpiration().before(new Date());
  }
}
