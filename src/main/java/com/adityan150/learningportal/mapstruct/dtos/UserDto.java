package com.adityan150.learningportal.mapstruct.dtos;

import lombok.Data;

@Data
public class UserDto {
	
	private long id;
	
	private String name;
	
	private String email;
	
	private String role;

}
