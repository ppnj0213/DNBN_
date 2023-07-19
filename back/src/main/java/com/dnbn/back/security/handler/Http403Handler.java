package com.dnbn.back.security.handler;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.dnbn.back.common.exception.ErrorCode;
import com.dnbn.back.common.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Http403Handler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");

		ErrorCode errorCode = ErrorCode.NO_ACCESS;

		log.error(errorCode.getMessage());

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(errorCode.getMessage())
			.httpStatus(errorCode.getHttpStatus())
			.build();

		objectMapper.writeValue(response.getWriter(), errorResponse);
	}
}
