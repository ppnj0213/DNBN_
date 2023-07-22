package com.dnbn.back.security.handler;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

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
public class Http401Handler implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");

		ErrorCode errorCode = ErrorCode.LOGIN_NEEDED;

		log.error(errorCode.getMessage());

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(errorCode.getMessage())
			.httpStatus(errorCode.getHttpStatus())
			.errorCode(errorCode)
			.build();

		objectMapper.writeValue(response.getWriter(), errorResponse);
	}
}
