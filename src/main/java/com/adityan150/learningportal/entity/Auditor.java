package com.adityan150.learningportal.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@MappedSuperclass
public class Auditor {
	
	@Column(name = "created_on", updatable = false)
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Column(name = "updated_on")
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

}
