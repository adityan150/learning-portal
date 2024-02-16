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
import com.adityan150.learningportal.utils.InputValidator;

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
	
	public CourseResponseDto getCourseById(long id) {
		Course course = courseRepository.findById(id).orElse(null);
		if (course == null)
			return null;
		return CourseMapper.INSTANCE.courseToCourseGetRequestDto(course);
	}
	
	public CourseResponseDto createCourse(CourseCreateRequestDto courseCreateRequestDto) throws Exception {
		Optional<User> userOptional = userRepository.findById(1L);
		if (userOptional.isEmpty()) {
			throw new Exception("Invalid request. User not present.");
		}
		User user = userOptional.get();
		Course course = CourseMapper.INSTANCE.courseCreateRequestDtoToCourse(courseCreateRequestDto, user);
		course = courseRepository.save(course);
		return CourseMapper.INSTANCE.courseToCourseGetRequestDto(course);
	}

	public CourseResponseDto updateCourse(long id, CourseUpdateDto courseUpdateDto) {
		Course course = courseRepository.findById(id).orElse(null);
		if (course == null) {
			return null;
		}
		
		String title = InputValidator.isValidInputString(courseUpdateDto.getTitle()) ? 
				courseUpdateDto.getTitle() 
				:
				course.getTitle();
		
		String description = InputValidator.isValidInputString(courseUpdateDto.getDescription()) ?
				courseUpdateDto.getDescription() 
				:
				course.getDescription();
		
		int durationHours = courseUpdateDto.getDurationHours();
		
		String category = InputValidator.isValidInputString(courseUpdateDto.getCategory()) ?
				courseUpdateDto.getCategory() 
				:
				course.getCategory();
		
		course.setTitle(title);
		course.setDescription(description);
		course.setDurationHours(durationHours);
		course.setCategory(category);
		
		courseRepository.save(course);
		return CourseMapper.INSTANCE.courseToCourseGetRequestDto(course);
	}
	
	public String deleteCourse(long id) {
		try {
			courseRepository.deleteById(id);
			return "Course with id " + id + " deleted.";
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Error.";
		}
	}
}
