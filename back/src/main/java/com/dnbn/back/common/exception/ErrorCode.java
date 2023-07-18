package com.dnbn.back.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	MEMBER_NOT_FOUND("MEMBER", "사용자를 찾을 수 없습니다", HttpStatus.NOT_FOUND);

	private String code;
	private String message;
	private HttpStatus httpStatus;
}
