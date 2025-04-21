package org.example.expert.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {

    TOKEN_REQUIRED(HttpStatus.BAD_REQUEST, "JWT 토큰이 필요합니다."),
    INVALID_JWT_CLAIMS(HttpStatus.BAD_REQUEST, "JWT claims 정보가 유효하지 않습니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "유효하지 않는 JWT 서명입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.BAD_REQUEST, "지원되지 않는 JWT 토큰입니다."),
    INVALID_JWT_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 JWT 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
