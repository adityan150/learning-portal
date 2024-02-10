package com.adityan150.learningportal.mapstruct.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.adityan150.learningportal.entity.User;
import com.adityan150.learningportal.mapstruct.dtos.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mapping(target = "published", source = "user.published")
	public UserDto userToUserDto(User user);
	
//	User userDtoToUserEntity(UserDto userDto);
	
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	

}
