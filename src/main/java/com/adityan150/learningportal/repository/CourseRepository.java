package com.adityan150.learningportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adityan150.learningportal.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
