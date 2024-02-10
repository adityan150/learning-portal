package com.adityan150.learningportal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adityan150.learningportal.mapstruct.dtos.CourseCreateRequestDto;
import com.adityan150.learningportal.mapstruct.dtos.CourseResponseDto;
import com.adityan150.learningportal.mapstruct.dtos.CourseUpdateDto;
import com.adityan150.learningportal.service.CourseService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/course")
public class CourseController {
		
	private CourseService courseService;
	
//	 create course
	@PostMapping("/create")
	public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseCreateRequestDto courseCreateRequestDto) {
		try {
		CourseResponseDto createdCourse = courseService.createCourse(courseCreateRequestDto);
		return new ResponseEntity<>(createdCourse, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}
	// update course
	
	@PutMapping("/{id}")
	public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable("id") long id, @RequestBody CourseUpdateDto courseUpdateDto) {
		CourseResponseDto updatedCourse = courseService.updateCourse(id, courseUpdateDto);
		if (updatedCourse == null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
	}
	
	// get all courses
	@GetMapping("/all")
	public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
		List<CourseResponseDto> courseDtoList = courseService.getAllCourses();
		
		if (courseDtoList.isEmpty()) {
			return new ResponseEntity<>(courseDtoList, HttpStatus.NO_CONTENT);
		}
				
		return new ResponseEntity<>(courseDtoList, HttpStatus.OK);
	}
	
	
	// delete course

}
