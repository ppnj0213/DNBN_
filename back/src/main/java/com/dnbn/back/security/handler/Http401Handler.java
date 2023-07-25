package com.dnbn.back.security.handler;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.dnbn.back.common.error.ErrorCode;
import com.dnbn.back.common.error.ErrorResponse;
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
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		ErrorCode errorCode = ErrorCode.LOGIN_NEEDED;

		log.error(errorCode.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(errorCode.getMessage(), errorCode.getHttpStatus());

		objectMapper.writeValue(response.getWriter(), errorResponse);
	}
}
