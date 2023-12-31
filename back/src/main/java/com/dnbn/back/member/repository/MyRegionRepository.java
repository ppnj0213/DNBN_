package com.dnbn.back.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dnbn.back.member.entity.MyRegion;

@Repository
public interface MyRegionRepository extends JpaRepository<MyRegion, Long> {
	List<MyRegion> findByMemberId(Long memberId);
	void deleteByMemberId(Long memberId);
}
