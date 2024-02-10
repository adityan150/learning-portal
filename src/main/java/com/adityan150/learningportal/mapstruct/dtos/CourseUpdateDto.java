package com.adityan150.learningportal.mapstruct.dtos;

import lombok.Data;

@Data
public class CourseUpdateDto {
	
	private String title;
	
	private String description;
	
	private int durationHours;
	
	private String category;

}
