package com.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.learning.entity.Authority;
import com.learning.entity.CustomerInfo;
import com.learning.entity.User;
import com.learning.entity.UserRole;
import com.learning.exception.UserAlreadyExistException;
import com.learning.repo.AuthorityRepository;
import com.learning.repo.CustomerInfoRepository;
import com.learning.repo.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class CustomerService {
	private UserRepository userRepository;
	private AuthorityRepository authorityRepositoy;
	private CustomerInfoRepository customerInfoRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public CustomerService(UserRepository userRepository, AuthorityRepository authorityRepositoy,
			CustomerInfoRepository customerInfoRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.authorityRepositoy = authorityRepositoy;
		this.customerInfoRepository = customerInfoRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public User signUp(User user) {
		String username = user.getUsername();		String fullname = user.getFullname();
		
		if(userRepository.existsById(username)) {
			throw new UserAlreadyExistException("Username: " + username + " already exists.");
		}
		
		CustomerInfo info = new CustomerInfo();
		info.setUsername(username);
		info.setFullname(fullname);
		customerInfoRepository.save(info);
		
		authorityRepositoy.save(new Authority(username, UserRole.CUSTOMER.name()));
		
		user.setId(username.hashCode());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		return userRepository.save(user);
	}
}
