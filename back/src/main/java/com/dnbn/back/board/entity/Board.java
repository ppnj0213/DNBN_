package com.dnbn.back.board.entity;

import static org.springframework.util.StringUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dnbn.back.board.model.BoardUpdateDto;
import com.dnbn.back.comment.entity.Comment;
import com.dnbn.back.common.entity.BaseTimeEntity;
import com.dnbn.back.common.error.exception.BoardException;
import com.dnbn.back.common.error.ErrorCode;
import com.dnbn.back.member.entity.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Board extends BaseTimeEntity {

	@Id @GeneratedValue
	@Column(name = "board_id")
	private Long id;
	private String sido_code;
	private String sigoon_code;
	private String dong_code;
	private String content;
	private String writer;
	private String openYn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	public void setMember(Member member) {
		this.member = member;
	}

	public void editBoard(BoardUpdateDto boardUpdateDto) {
		if (hasText(boardUpdateDto.getContent())) {
			content = boardUpdateDto.getContent();
		}
	}

	public void validateRequiredFields() {
		if (sido_code == null || sido_code.isEmpty()) {
            throw new BoardException(ErrorCode.REGION_CODE_EMPTY);
        }

        if (sigoon_code == null || sigoon_code.isEmpty()) {
            throw new BoardException(ErrorCode.REGION_CODE_EMPTY);
        }

        if (dong_code == null || dong_code.isEmpty()) {
            throw new BoardException(ErrorCode.REGION_CODE_EMPTY);
        }

        if (content == null || content.isEmpty()) {
            throw new BoardException(ErrorCode.CONTENT_EMPTY);
        }

        if (writer == null || writer.isEmpty()) {
            throw new BoardException(ErrorCode.NICKNAME_EMPTY);
        }
	}

	public boolean matchMember(Long memberId) {
		if (member.getId() != memberId) {
			return false;
		}
		return true;
	}
}
