package com.dnbn.back.common.error.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dnbn.back.common.error.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

	@ExceptionHandler({MemberException.class, BoardException.class, CommentException.class})
	protected ResponseEntity<ErrorResponse> handleMemberException(CommonException e) {
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error(
				LOG_FORMAT,
				e.getClass().getSimpleName(),
				e.getStatusCode(),
				"Spring Validation 실패"
			);
		BindingResult bindingResult = e.getBindingResult();
		List<String> emptyFields = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String errorMsg = fieldError.getField() + " - " + fieldError.getDefaultMessage();
			log.error(errorMsg);
			emptyFields.add(errorMsg);
		}

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(emptyFields.toString(), HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
		log.error(e.getMessage());
		log.error("StackTrace : {}", e.getStackTrace());
		e.printStackTrace();

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ErrorResponse("알 수 없는 오류가 발생했습니다. 관리자에게 문의하세요.", HttpStatus.INTERNAL_SERVER_ERROR));
	}

}
