package com.adityan150.learningportal.controller;

import java.time.Duration;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adityan150.learningportal.entity.Course;
import com.adityan150.learningportal.entity.User;
import com.adityan150.learningportal.mapstruct.dtos.UserDto;
import com.adityan150.learningportal.mapstruct.dtos.UserLoginDto;
import com.adityan150.learningportal.mapstruct.mappers.UserMapper;
import com.adityan150.learningportal.repository.UserRepository;
import com.adityan150.learningportal.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;
	
	/* Authentication */
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserLoginDto userLoginDto) {
		log.info("UserLoginDto: " + userLoginDto.toString());
		
		UserDto user = userService.authenticateUser(userLoginDto.getEmail());
		
		
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		log.info("Authenticated user: " + user.toString());
		
		try {
			String cookieValue = "name="+user.getName()+"&email=" + user.getEmail()+ "&role="+user.getRole();
			ResponseCookie cookie = ResponseCookie.fromClientResponse("user", cookieValue)
					.secure(false)
					.httpOnly(false)
					.maxAge(Duration.ofDays(1))
					.build();
			log.info(cookie.toString());
			// Add cookie to response headers
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
			
			// return response with headers and body
			return ResponseEntity.ok().headers(headers).body("Cookie created. Logged In.");
		}
		catch (Exception e) {
			log.error("Failed to set cookie.");
			e.printStackTrace();
			
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logoutUser() {
		// Set the cookie in headers
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, "user=");
		
		// return response with set headers
		return ResponseEntity.ok().headers(headers).body(null);
	}
	
	
	/* Admin functions */
	// CRUD on other users
	
	@GetMapping("/all")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> userDtoList = userService.getAllUsers();
		
		if (userDtoList.isEmpty()) {
			return new ResponseEntity<>(userDtoList, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(userDtoList, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findUserById(@PathVariable("id") long id) {
		log.info("" + id);
		UserDto userDto = userService.findUserById(id);
		
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
		UserDto createdUser = userService.createUser(user);
		
		if (createdUser == null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(createdUser, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
		if (userService.deleteUser(id)) {
			return new ResponseEntity<>("Deleted user with id " + id, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Failed to process delete request.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("id") long id, @RequestBody UserDto userDto) {
		UserDto userDtoUpdated = userService.updateUser(id, userDto);
		
		if (userDtoUpdated == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(userDtoUpdated, HttpStatus.OK);
		
	}
	
	
	
	/* Learner Functions */
	
	@PutMapping("/user/{id}/enroll/{course_id}")
	public ResponseEntity<Course> enrollUserToCourse(@PathVariable("id") long id, @PathVariable("course_id") long courseId) {
		return null;
	}
	
	@PutMapping("/user/{id}/unenroll/{course_id}")
	public ResponseEntity<Course> unenrollUserFromCourse(@PathVariable("id") long id, @PathVariable("course_id") long courseId) {
		return null;
	}
	
	@PutMapping("/user/{id}/favorite/{course_id}")
	public ResponseEntity<Course> addCourseToFavorite(@PathVariable("id") long id, @PathVariable("course_id") long courseId) {
		return null;
	}
	
	@PutMapping("/user/{id}/removefavorite/{course_id}")
	public ResponseEntity<Course> removeCourseFromFavorite(@PathVariable("id") long id, @PathVariable("course_id") long courseId) {
		return null;
	}
	
	/* Author functions */
	
	// CRUD on courses
	
	
}
