package com.adityan150.learningportal.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User extends Auditor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	private String name;
	
	private String role;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "enrollment", 
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id")
	)
	private List<Course> enrolledCourses = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "favorite", 
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id")
	)
	private List<Course> favoriteCourses = new ArrayList<>();

}
