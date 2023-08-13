package com.dnbn.back.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnbn.back.member.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
	List<Region> findByMemberId(Long memberId);
	void deleteByMemberId(Long memberId);
}
