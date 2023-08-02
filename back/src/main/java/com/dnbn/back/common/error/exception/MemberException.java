package com.dnbn.back.common.error.exception;

import org.springframework.http.HttpStatus;

import com.dnbn.back.common.error.ErrorCode;

public class MemberException extends CommonException {
	private final ErrorCode errorCode;
	private final String message;
	private final HttpStatus httpStatus;

	public MemberException(ErrorCode errorCode) {
		this(errorCode, errorCode.getMessage(), errorCode.getHttpStatus());
	}

	public MemberException(ErrorCode errorCode, String message, HttpStatus httpStatus) {
		super(errorCode, message, httpStatus);
		this.errorCode = errorCode;
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
