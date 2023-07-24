package com.dnbn.back.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

	@ExceptionHandler(MemberException.class)
	protected ResponseEntity<ErrorResponse> handleMemberException(MemberException e) {
		log.error(
				LOG_FORMAT,
				e.getClass().getSimpleName(),
				e.getErrorCode(),
				e.getMessage()
			);
		log.error("StackTrace : {}", e.getStackTrace());

		return ResponseEntity
			.status(e.getHttpStatus())
			.body(new ErrorResponse(e.getMessage(), e.getHttpStatus()));
	}

	@ExceptionHandler(BoardException.class)
	protected ResponseEntity<ErrorResponse> handleBoardException(BoardException e) {
		log.error(
				LOG_FORMAT,
				e.getClass().getSimpleName(),
				e.getErrorCode(),
				e.getMessage()
			);
		log.error("StackTrace : {}", e.getStackTrace());

		return ResponseEntity
			.status(e.getHttpStatus())
			.body(new ErrorResponse(e.getMessage(), e.getHttpStatus()));
	}

	@ExceptionHandler(CommentException.class)
	protected ResponseEntity<ErrorResponse> handleCommentException(CommentException e) {
		log.error(
				LOG_FORMAT,
				e.getClass().getSimpleName(),
				e.getErrorCode(),
				e.getMessage()
			);
		log.error("StackTrace : {}", e.getStackTrace());

		return ResponseEntity
			.status(e.getHttpStatus())
			.body(new ErrorResponse(e.getMessage(), e.getHttpStatus()));
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
		log.error(e.getMessage());
		log.error("StackTrace : {}", e.getStackTrace());

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ErrorResponse("알 수 없는 오류가 발생했습니다. 관리자에게 문의하세요.", HttpStatus.INTERNAL_SERVER_ERROR));
	}

}
