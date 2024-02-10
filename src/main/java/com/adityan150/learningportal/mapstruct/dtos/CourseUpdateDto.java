package com.adityan150.learningportal.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseUpdateDto {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("duration")
	private int durationHours;
	
	@JsonProperty("category")
	private String category;

}
