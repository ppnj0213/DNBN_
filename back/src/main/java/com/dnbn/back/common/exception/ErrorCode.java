package com.dnbn.back.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	MEMBER_NOT_FOUND("MEMBER", "사용자를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
	LOGIN_FAILED("LOGIN", "아이디 혹은 비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
	NO_ACCESS("ACCESS", "해당 페이지에 대한 권한이 없습니다.", HttpStatus.UNAUTHORIZED),
	LOGIN_NEEDED("LOGIN", "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
	ID_DUPLICATED("ID_VALID", "중복되는 아이디가 존재합니다.", HttpStatus.UNPROCESSABLE_ENTITY),
	NICKNAME_DUPLICATED("NICKNAME_VALID", "중복되는 닉네임이 존재합니다.", HttpStatus.UNPROCESSABLE_ENTITY),

	USER_ID_IS_EMPTY("MEMBER", "userId 입력은 필수입니다.", HttpStatus.BAD_REQUEST),
	USER_PW_IS_EMPTY("MEMBER", "userPw 입력은 필수입니다.", HttpStatus.BAD_REQUEST),
	NICKNAME_IS_EMPTY("MEMBER", "nickname 입력은 필수입니다.", HttpStatus.BAD_REQUEST),
	MY_REGION_NOT_ENOUGH("MY_REGION", "MyRegion에 입력되지 않은 값이 있습니다.", HttpStatus.BAD_REQUEST);

	private String code;
	private String message;
	private HttpStatus httpStatus;
}
