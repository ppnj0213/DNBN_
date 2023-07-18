package com.dnbn.back.common.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorDto> handleNotFoundException(MemberException e) {
		return ResponseEntity.ok(new ErrorDto(e.getMessage(), e.getHttpStatus()));
	}

}
