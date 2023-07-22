package com.dnbn.back.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	@ExceptionHandler(MemberException.class)
	protected ResponseEntity<ErrorResponse> handleMemberException(MemberException e) {
		log.error(e.getMessage());
		return ResponseEntity.ok(new ErrorResponse(e.getMessage(), e.getHttpStatus(), e.getErrorCode()));
	}

	@ExceptionHandler(BoardException.class)
	protected ResponseEntity<ErrorResponse> handleBoardException(BoardException e) {
		log.error(e.getMessage());
		return ResponseEntity.ok(new ErrorResponse(e.getMessage(), e.getHttpStatus(), e.getErrorCode()));
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
		log.error(e.getMessage());
		e.printStackTrace();
		return ResponseEntity.ok(new ErrorResponse("알 수 없는 오류가 발생했습니다. 관리자에게 문의하세요.", HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR));
	}

}
