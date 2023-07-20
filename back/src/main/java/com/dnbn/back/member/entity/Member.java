package com.dnbn.back.member.entity;

import static org.springframework.util.StringUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import com.dnbn.back.board.entity.Board;
import com.dnbn.back.common.entity.BaseTimeEntity;
import com.dnbn.back.common.exception.ErrorCode;
import com.dnbn.back.common.exception.MemberException;
import com.dnbn.back.member.model.MemberUpdateDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@ToString(callSuper = true)
@Builder
public class Member extends BaseTimeEntity {

	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String userId;
	private String userPw;
	private String nickname;
	@Enumerated(EnumType.STRING)
	private Role role;
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MyRegion> myRegions = new ArrayList<>();
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Board> boards = new ArrayList<>();

	public void editMember(MemberUpdateDto memberUpdateDto) {
		if (hasText(memberUpdateDto.getUserPw())) {
			userPw = memberUpdateDto.getUserPw();
		}
		if (hasText(memberUpdateDto.getNickname())) {
			nickname = memberUpdateDto.getNickname();
		}
	}

	public void validateRequiredFields() {
		if (userId == null || userId.isEmpty()) {
            throw new MemberException(ErrorCode.USER_ID_IS_EMPTY);
        }

        if (userPw == null || userPw.isEmpty()) {
            throw new MemberException(ErrorCode.USER_PW_IS_EMPTY);
        }

        if (nickname == null || nickname.isEmpty()) {
            throw new MemberException(ErrorCode.NICKNAME_IS_EMPTY);
        }
	}
}
