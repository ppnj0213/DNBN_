package com.dnbn.back.common.exception;

import org.springframework.http.HttpStatus;

public class BoardException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String message;
	private final HttpStatus httpStatus;

	public BoardException(ErrorCode errorCode) {
		this(errorCode, errorCode.getMessage(), errorCode.getHttpStatus());
	}

	public BoardException(ErrorCode errorCode, String message, HttpStatus status) {
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
