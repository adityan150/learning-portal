package com.adityan150.learningportal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.adityan150.learningportal.entity.Course;
import com.adityan150.learningportal.entity.User;
import com.adityan150.learningportal.mapstruct.dtos.CourseCreateRequestDto;
import com.adityan150.learningportal.mapstruct.dtos.CourseResponseDto;
import com.adityan150.learningportal.mapstruct.dtos.CourseUpdateDto;
import com.adityan150.learningportal.mapstruct.mappers.CourseMapper;
import com.adityan150.learningportal.repository.CourseRepository;
import com.adityan150.learningportal.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CourseService {
	
	private CourseRepository courseRepository;
	private UserRepository userRepository;
	
	public List<CourseResponseDto> getAllCourses() {
		List<Course> courseList = courseRepository.findAll();
		
		if (courseList.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<CourseResponseDto> courseDtoList = new ArrayList<>();
		
		courseList.forEach(course -> courseDtoList.add(CourseMapper.INSTANCE.courseToCourseGetRequestDto(course)));
		
		return courseDtoList;
	}
	
	public CourseResponseDto createCourse(CourseCreateRequestDto courseCreateRequestDto) throws Exception {
		Optional<User> userOptional = userRepository.findById(13L);
		if (userOptional.isEmpty()) {
			throw new Exception("Invalid request. User not present.");
		}
		User user = userOptional.get();
		Course course = CourseMapper.INSTANCE.courseCreateRequestDtoToCourse(courseCreateRequestDto, user);
		course = courseRepository.save(course);
		return CourseMapper.INSTANCE.courseToCourseGetRequestDto(course);
	}

	public CourseResponseDto updateCourse(long id, CourseUpdateDto courseUpdateDto) {
		// TODO Auto-generated method stub
		return null;
	}


}
