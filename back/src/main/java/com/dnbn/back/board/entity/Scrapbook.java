package com.dnbn.back.board.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dnbn.back.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Scrapbook {
	@Id
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Id
	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;
}
