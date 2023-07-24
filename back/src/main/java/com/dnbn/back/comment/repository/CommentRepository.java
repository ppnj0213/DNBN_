package com.dnbn.back.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnbn.back.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
