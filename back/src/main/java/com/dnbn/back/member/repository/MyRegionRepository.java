package com.dnbn.back.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnbn.back.member.entity.MyRegion;

public interface MyRegionRepository extends JpaRepository<MyRegion, Long> {
	List<MyRegion> findByMemberId(Long memberId);
}
