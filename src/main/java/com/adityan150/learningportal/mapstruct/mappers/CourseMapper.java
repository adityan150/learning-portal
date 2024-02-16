package com.adityan150.learningportal.mapstruct.mappers;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import com.adityan150.learningportal.entity.Course;
import com.adityan150.learningportal.entity.User;
import com.adityan150.learningportal.mapstruct.dtos.CourseCreateRequestDto;
import com.adityan150.learningportal.mapstruct.dtos.CourseResponseDto;


@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CourseMapper {

	@Mapping(target = "authorName", source = "author.name" )
    CourseResponseDto courseToCourseGetRequestDto(Course course);
	
	default int map(Set<User> enrolled) {
		return enrolled == null? 0 : enrolled.size();
	}
	
	@Mapping(target = "author", source = "author")
    Course courseCreateRequestDtoToCourse(CourseCreateRequestDto coursePostRequestDto, User author);


    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
}
