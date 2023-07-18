package com.dnbn.back.security.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.dnbn.back.member.entity.Member;

import lombok.Data;

public class MemberDetails extends User {

	private final Long id;

	public MemberDetails(Member member) {
		super(member.getUserId(), member.getUserPw(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
		this.id = member.getId();
	}

	public Long getId() {
		return id;
	}
}
