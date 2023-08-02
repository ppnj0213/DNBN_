package com.dnbn.back.common.error.exception;

import org.springframework.http.HttpStatus;

import com.dnbn.back.common.error.ErrorCode;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{
	private final ErrorCode errorCode;
	private final String message;
	private final HttpStatus httpStatus;

	public CommonException(ErrorCode errorCode, String message, HttpStatus httpStatus) {
		this.errorCode = errorCode;
		this.message = message;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
