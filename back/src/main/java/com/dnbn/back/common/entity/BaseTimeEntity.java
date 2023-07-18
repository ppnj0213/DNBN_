package com.dnbn.back.common.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@ToString
public class BaseTimeEntity {

	@CreatedDate
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;
	@LastModifiedDate
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;
}
