package com.dnbn.back.common.exception;

import org.springframework.http.HttpStatus;

public class MemberException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String message;
	private final HttpStatus httpStatus;

	public MemberException(ErrorCode errorCode) {
		this(errorCode, errorCode.getMessage(), errorCode.getHttpStatus());
	}

	public MemberException(ErrorCode errorCode, String message, HttpStatus status) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
		this.httpStatus = status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
