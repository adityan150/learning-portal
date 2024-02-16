package com.adityan150.learningportal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

import com.adityan150.learningportal.entity.Course;
import com.adityan150.learningportal.entity.User;
import com.adityan150.learningportal.mapstruct.dtos.UserDto;
import com.adityan150.learningportal.mapstruct.mappers.UserMapper;
import com.adityan150.learningportal.repository.CourseRepository;
import com.adityan150.learningportal.repository.UserRepository;
import com.adityan150.learningportal.utils.InputValidator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {
	
	private UserRepository userRepository;
	private CourseRepository courseRepository;
	
	public UserDto authenticateUser(String email) {
		List<User> userList = userRepository.findByEmail(email);
		
		if (userList.size() != 1) {
			return null;
		}
		return UserMapper.INSTANCE.userToUserDto(userList.get(0));
	}
	
	public List<UserDto> getAllUsers() {
		List<User> userList = userRepository.findAll();
		
		if (userList.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<UserDto> userDtoList = new ArrayList<>();
		userList.forEach(user -> userDtoList.add(UserMapper.INSTANCE.userToUserDto(user)));
		return userDtoList;
	}
	
	public UserDto findUserById(long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isEmpty())
			return null;
		User user = userOptional.get();
		log.info(user.toString());
		UserDto userDto = UserMapper.INSTANCE.userToUserDto(user);
		log.info(userDto.toString());
		return userDto;
	}
	
	public UserDto createUser(UserDto userDto) {
		String email = userDto.getEmail();
		String name = userDto.getName();
		String role = userDto.getRole();
		
		try {
			// validate fields
			if (! InputValidator.isValidEmail(email) || 
				! InputValidator.isValidInputString(name)|| 
				role == null || 
				!(role.equals("ADMIN") || role.equals("AUTHOR") || role.equals("LEARNER"))
			) {
				log.error("Values to create user are not valid.");
				throw new Exception();
			}
			
			User user = new User();
			user.setName(name);
			user.setEmail(email);
			user.setRole(role);
		
			return UserMapper.INSTANCE.userToUserDto(userRepository.save(user));
		}
		catch (Exception e) {
			log.error("Failed to save the user in database");
			e.printStackTrace();
			return null;
		}
		
	}
	
	public boolean deleteUser(long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isEmpty()) {
			return false;
		}
		userRepository.deleteById(id);
		return true;
	}
	
	public UserDto updateUser(long id, UserDto userDto) {
		Optional<User> response = userRepository.findById(id);
		
		if (response.isEmpty()) {
			return null;
		}
		
		User user = response.get();
		
		String email = InputValidator.isValidEmail(userDto.getEmail())? 
				userDto.getEmail() 
				:
				user.getEmail();
	
		String name = InputValidator.isValidInputString(userDto.getName())?
				userDto.getName() 
				:
				user.getName();
		
		String role = InputValidator.isValidInputString(userDto.getRole())?
				userDto.getRole()
				:
				user.getRole();
		
		if (!(role.equals("ADMIN") || role.equals("AUTHOR") || role.equals("LEARNER"))) {
			return null;
		}
		
		user.setEmail(email);
		user.setName(name);
		user.setRole(role);
		
		return UserMapper.INSTANCE.userToUserDto(userRepository.save(user));
	}
	
	/* Learner Functions */
	
	public String enrollToCourse(long userId, long courseId) {
		User user = userRepository.findById(userId).orElse(null);
		Course course = courseRepository.findById(courseId).orElse(null);
		
		if (user == null || course == null) {
			return null;
		}
		
		user.getEnrolledCourses().add(course);
		userRepository.save(user);
		return "User " +userId+ " enrolled to course " + courseId;
	}
	
	public String favoriteCourse(long userId, long courseId) {
		User user = userRepository.findById(userId).orElse(null);
		Course course = courseRepository.findById(courseId).orElse(null);

		if (user == null || course == null) {
			return null;
		}
		
		user.getFavoriteCourses().add(course);
		userRepository.save(user);
		return "Course " +courseId+ " marked to favorite by user " + userId;
	}
	
	public String unenrollCourse(long userId, long courseId) {
		User user = userRepository.findById(userId).orElse(null);
		Course course = courseRepository.findById(courseId).orElse(null);
		
		if (user == null || course == null) {
			return null;
		}
		
		user.getEnrolledCourses().remove(course);
		userRepository.save(user);
		
		return "User " +userId+ " unenrolled from course " + courseId;
	}
	
	public String removeFavorite(long userId, long courseId) {
		User user = userRepository.findById(userId).orElse(null);
		Course course = courseRepository.findById(courseId).orElse(null);
		
		if (user == null || course == null) {
			return null;
		}
		
		user.getFavoriteCourses().remove(course);
		userRepository.save(user);
		
		return "User " +userId+ " removed course " + courseId + " from favorites";
	}
	
	
	
	
}
