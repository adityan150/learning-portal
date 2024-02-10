package com.adityan150.learningportal.mapstruct.dtos;

import java.util.Set;

import com.adityan150.learningportal.entity.Course;

import lombok.Data;

@Data
public class UserDto {
	
	private long id;
	
	private String name;
	
	private String email;
	
	private String role;
	
	private Set<Course> published;
}
