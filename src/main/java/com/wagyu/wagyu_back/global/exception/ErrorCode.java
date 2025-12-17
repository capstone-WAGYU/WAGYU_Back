package com.wagyu.wagyu_back.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 올바르지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),

    PET_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 반려동물 정보를 찾을 수 없습니다."),
    FORBIDDEN_PET_UPDATE(HttpStatus.FORBIDDEN, "해당 반려동물 정보를 수정할 권한이 없습니다."),
    FORBIDDEN_PET_DELETE(HttpStatus.FORBIDDEN, "해당 반려동물 정보를 삭제할 권한이 없습니다."),
    MAXIMUM_PET_COUNT(HttpStatus.FORBIDDEN, "더 이상 반려동물을 추가할 수 없습니다."),

    BREED_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 견종을 찾을 수 없습니다."),

    HOSPITAL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 병원이 존재하지 않습니다."),
    HOSPITAL_NO_SCHEDULE(HttpStatus.NOT_FOUND, "병원 일정이 존재하지 않습니다."),
    FORBIDDEN_UPDATE_HOSPITAL(HttpStatus.FORBIDDEN, "해당 병원의 정보를 수정할 권한이 없습니다.");

    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }
}
