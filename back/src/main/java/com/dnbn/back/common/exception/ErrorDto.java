package com.dnbn.back.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDto {
	private String message;
	private HttpStatus httpStatus;
}
