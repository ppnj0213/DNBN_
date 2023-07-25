package com.dnbn.back.security.handler;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

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
public class Http403Handler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		ErrorCode errorCode = ErrorCode.NO_ACCESS_PAGE;

		log.error(errorCode.getMessage());

		ErrorResponse errorResponse = new ErrorResponse(errorCode.getMessage(), errorCode.getHttpStatus());

		objectMapper.writeValue(response.getWriter(), errorResponse);
	}
}
