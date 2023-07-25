package com.dnbn.back.common.error.exception;

import org.springframework.http.HttpStatus;

import com.dnbn.back.common.error.ErrorCode;

public class CommentException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String message;
	private final HttpStatus httpStatus;

	public CommentException(ErrorCode errorCode) {
		this(errorCode, errorCode.getMessage(), errorCode.getHttpStatus());
	}

	public CommentException(ErrorCode errorCode, String message, HttpStatus status) {
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
