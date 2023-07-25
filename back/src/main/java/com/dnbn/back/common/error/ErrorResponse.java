package com.dnbn.back.common.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private String message;
	private HttpStatus httpStatus;

	public ErrorResponse(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
