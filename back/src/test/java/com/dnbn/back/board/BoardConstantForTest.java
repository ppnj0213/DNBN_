package com.dnbn.back.board;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BoardConstantForTest {
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public static final String BASE_URL = "/api/boards";
	public static final String CONTENTS = "test contents";
	public static final String WRITER = "tester";
	public static final Long BOARD_ID = 1L;
}
