package com.dnbn.back.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dnbn.back.board.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
	Optional<Like> findByBoardIdAndMemberId(Long boardId, Long memberId);
	@Query("select count(l) from Like l where l.board.id = :boardId")
	Long getCountByBoardId(@Param("boardId") Long boardId);
}
