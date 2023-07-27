package com.dnbn.back.common.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	// 회원가입
	ID_DUPLICATED("MEMBER", "중복되는 아이디가 존재합니다.", HttpStatus.UNPROCESSABLE_ENTITY),
	NICKNAME_DUPLICATED("MEMBER", "중복되는 닉네임이 존재합니다.", HttpStatus.UNPROCESSABLE_ENTITY),
	USER_ID_EMPTY("MEMBER", "userId 입력은 필수입니다.", HttpStatus.BAD_REQUEST),
	USER_PW_EMPTY("MEMBER", "userPw 입력은 필수입니다.", HttpStatus.BAD_REQUEST),
	NICKNAME_EMPTY("MEMBER", "nickname 입력은 필수입니다.", HttpStatus.BAD_REQUEST),
	MY_REGION_NOT_ENOUGH("MY_REGION", "내 동네에 입력되지 않은 값이 있습니다.", HttpStatus.BAD_REQUEST),
	MAIN_REGION_UNACCEPTABLE("MY_REGION", "대표 동네는 1개만 선택할 수 있으며, 필수입니다.", HttpStatus.BAD_REQUEST),

	// 로그인
	MEMBER_NOT_FOUND("MEMBER", "사용자를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
	LOGIN_FAILED("MEMBER", "아이디 혹은 비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
	
	// 권한
	NO_ACCESS_PAGE("ACCESS", "해당 페이지에 대한 권한이 없습니다.", HttpStatus.UNAUTHORIZED),
	LOGIN_NEEDED("ACCESS", "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
	NO_ACCESS_BOARD("BOARD", "해당 게시글에 대한 수정/삭제 권한이 없습니다.", HttpStatus.UNAUTHORIZED),
	NO_ACCESS_COMMENT("COMMENT", "해당 댓글에 대한 삭제 권한이 없습니다.", HttpStatus.UNAUTHORIZED),

	// 게시판
	BOARD_NOT_FOUND("BOARD", "게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
	ALREADY_DELETED("BOARD", "이미 삭제된 게시글입니다.", HttpStatus.NOT_FOUND),
	REGION_CODE_EMPTY("BOARD", "내 동네 코드 파라미터에 빈 값이 존재합니다.", HttpStatus.BAD_REQUEST),
	CONTENT_EMPTY("BOARD", "내용을 입력하세요.", HttpStatus.BAD_REQUEST),
	WRITER_EMPTY("BOARD", "작성자 파라미터에 빈 값이 존재합니다.", HttpStatus.BAD_REQUEST),
	OPEN_YN_EMPTY("BOARD", "공개 게시판 여부가 입력되지 않았습니다.", HttpStatus.BAD_REQUEST),

	// 댓글
	COMMENT_NOT_FOUND("COMMENT", "댓글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),

	INTERNAL_SERVER_ERROR("500", "알 수 없는 오류가 발생했습니다. 관리자에게 문의하세요.", HttpStatus.INTERNAL_SERVER_ERROR);

	private String code;
	private String message;
	private HttpStatus httpStatus;
}
