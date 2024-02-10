package com.adityan150.learningportal.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseResponseDto {
	
	@JsonProperty("id")
	private long id;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("description")
	private String description;

	@JsonProperty("category")
	private String category;
	
	@JsonProperty("durationHours")
	private int durationHours;
	
	@JsonProperty("author")
	private String authorName;
	
	@JsonProperty("enrolled")
	private int enrolledCount;
	
}
