package com.adityan150.learningportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adityan150.learningportal.entity.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>{
	public List<User> findByEmail(String email);
}
