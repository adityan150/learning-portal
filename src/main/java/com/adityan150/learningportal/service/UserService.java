package com.adityan150.learningportal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.adityan150.learningportal.entity.User;
import com.adityan150.learningportal.mapstruct.dtos.UserDto;
import com.adityan150.learningportal.mapstruct.mappers.UserMapper;
import com.adityan150.learningportal.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {
	
	private UserRepository userRepository;
	
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
			if (!isValidEmail(email) || name == null || 
					name.length() == 0 || role == null || 
					!(role.equals("ADMIN") || role.equals("AUTHOR") || role.equals("LEARNER")
							)) {
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
	
	boolean isValidEmail(String email) {
		final String EMAIL_REGEX =
	            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
	            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
		
		String email = userDto.getEmail();
		if (email == null || !isValidEmail(email)) {
				email = user.getEmail();
		}
		
		String name = userDto.getName();
		String role = userDto.getRole();
		
		if (role == null || !(role.equals("ADMIN") || role.equals("AUTHOR") || role.equals("LEARNER"))) {
			role = user.getRole();
		}
		
		user.setEmail(email);
		user.setName(name);
		user.setRole(role);
		
		return UserMapper.INSTANCE.userToUserDto(userRepository.save(user));
	}
}
