package com.dnbn.back.security.auth;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.dnbn.back.member.entity.Member;

import lombok.Getter;

@Getter
public class MemberDetails extends User {

	private final Long id;
	private final String nickname;

	public MemberDetails(Member member) {
		super(member.getUserId(), member.getUserPw(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
		this.id = member.getId();
		this.nickname = member.getNickname();
	}

}
