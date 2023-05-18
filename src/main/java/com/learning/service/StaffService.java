package com.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.Authority;
import com.learning.entity.User;
import com.learning.entity.UserRole;
import com.learning.exception.UserAlreadyExistException;
import com.learning.repo.AuthorityRepository;
import com.learning.repo.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class StaffService {

	
	UserRepository userRepo;
	AuthorityRepository authRepo;
	PasswordEncoder passwordEncoder;
	
	@Autowired
	public StaffService(UserRepository userRepo, AuthorityRepository authRepo, PasswordEncoder passwordEncoder) {
		super();
		this.userRepo = userRepo;
		this.authRepo = authRepo;
		this.passwordEncoder = passwordEncoder;
	}


	public User createStaff(User user) {
		String username = user.getUsername();
		if(userRepo.existsById(username)) {
			throw new UserAlreadyExistException("Username: " + username + " already exists.");
		}
		
		authRepo.save(new Authority(username, UserRole.STAFF.name()));
		
		user.setId(username.hashCode());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		return userRepo.save(user);
	}
}
