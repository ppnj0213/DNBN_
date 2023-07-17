package com.dnbn.back.member.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dnbn.back.member.repository.MyRegionRepository;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberService memberService;

}