package com.dnbn.back.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dnbn.back.comment.entity.Comment;
import com.dnbn.back.comment.model.CommentDetailDto;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query("select new com.dnbn.back.comment.model.CommentDetailDto(c.id, c.board.id, c.member.id, c.content, c.createdDate, c.modifiedDate) from  Comment c where c.board.id = :boardId order by c.id desc")
	Slice<CommentDetailDto> findCommentListWithSlice(Pageable pageable, @Param("boardId") Long boardId);
}
