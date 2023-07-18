package com.dnbn.back.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/")
	public String mainPage() {
		return "메인페이지 입니다.";
	}

	@GetMapping("/user")
	public String userPage() {
		return "사용자 입니다.";
	}

	@GetMapping("/admin")
	public String adminPage() {
		return "관리자 입니다.";
	}
}
