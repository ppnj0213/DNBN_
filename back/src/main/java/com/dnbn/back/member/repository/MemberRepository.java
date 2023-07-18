package com.dnbn.back.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dnbn.back.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUserId(String userId);
	boolean existsByUserId(String userId);
	boolean existsByNickname(String nickname);
	@Query("select m from Member m join fetch m.myRegions where m.id = :id")
	Member findByIdWithMyRegion(@Param("id") Long memberId);
}
