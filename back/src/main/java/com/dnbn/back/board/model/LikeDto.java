package com.dnbn.back.board.model;

import lombok.Data;

@Data
public class LikeDto {
	private boolean isLiked;
	private Long totalCount;
}
