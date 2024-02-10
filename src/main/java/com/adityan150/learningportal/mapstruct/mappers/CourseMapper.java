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
	@Mapping(target = "enrolledCount", source = "enrolled")
    CourseResponseDto courseToCourseGetRequestDto(Course course);
	
	default int map(Set<User> enrolled) {
		return enrolled == null? 0 : enrolled.size();
	}

    // Mapping for creating a new course
    @Mapping(target = "enrolled", ignore = true)
	@Mapping(target = "favorites", ignore = true)
	@Mapping(target = "author", source = "author")
    Course courseCreateRequestDtoToCourse(CourseCreateRequestDto coursePostRequestDto, User author);

    // Mapping for updating an existing course
//    @Mapping(target = "id", source = "courseUpdateRequestDto.id")
//    @Mapping(target = "title", source = "courseUpdateRequestDto.title")
//    @Mapping(target = "description", source = "courseUpdateRequestDto.description")
//    @Mapping(target = "category", source = "courseUpdateRequestDto.category")
//    @Mapping(target = "durationHours", source = "courseUpdateRequestDto.durationHours")
//    Course updateCourseFromCourseUpdateRequestDto(CourseUpdateRequestDto courseUpdateRequestDto, Course existingCourse);



    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
}
