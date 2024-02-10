package com.adityan150.learningportal.mapstruct.dtos;

import lombok.Data;

@Data
public class CourseCreateRequestDto {
	
	private String title;
	
	private String description;
	
	private String category;
	
	private int durationHours;

}
