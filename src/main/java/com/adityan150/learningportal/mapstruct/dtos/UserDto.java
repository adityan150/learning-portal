package com.adityan150.learningportal.mapstruct.dtos;

import java.util.Set;

import com.adityan150.learningportal.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDto {
	
	@JsonProperty("id")
	private long id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("role")
	private String role;
	
	@JsonProperty("published")
	private Set<Course> published;
}
