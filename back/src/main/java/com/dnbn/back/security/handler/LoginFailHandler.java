package com.dnbn.back.security.handler;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.dnbn.back.common.exception.ErrorCode;
import com.dnbn.back.common.exception.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoginFailHandler implements AuthenticationFailureHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");

		ErrorCode errorCode = ErrorCode.LOGIN_FAILED;

		log.error(errorCode.getMessage());

		ErrorDto errorDto = ErrorDto.builder()
			.message(errorCode.getMessage())
			.httpStatus(errorCode.getHttpStatus())
			.build();

		objectMapper.writeValue(response.getWriter(), errorDto);
	}
}
