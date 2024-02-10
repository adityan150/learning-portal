package com.adityan150.learningportal.mapstruct.dtos;

import lombok.Data;

@Data
public class CourseResponseDto {
	
	private long id;
	
	private String title;
	
	private String description;

	private String category;
	
	private int durationHours;
	
	private String authorName;
	
	private int enrolledCount;
	
}
