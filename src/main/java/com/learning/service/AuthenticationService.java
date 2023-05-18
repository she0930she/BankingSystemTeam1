package com.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.learning.entity.Authority;
import com.learning.entity.Token;
import com.learning.entity.User;
import com.learning.entity.UserRole;
import com.learning.exception.UserNotAuthorizedException;
import com.learning.exception.UserNotExistException;
import com.learning.repo.AuthorityRepository;
import com.learning.util.JwtUtil;


@Service
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private AuthorityRepository authorityRepository;
    private JwtUtil jwtUtil;
    
    @Autowired
	public AuthenticationService(AuthenticationManager authenticationManager, AuthorityRepository authorityRepository,
			JwtUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.authorityRepository = authorityRepository;
		this.jwtUtil = jwtUtil;
	}
    

    public Token authenticate(User user, UserRole role) throws UserNotExistException {
    	String username = user.getUsername();
    	String password = user.getPassword();

        try {    
        	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
           
        } catch (AuthenticationException exception) {
            throw new UserNotExistException("User Doesn't Exist or Not Authorized");
        }
        
        Authority authority = authorityRepository.findById(username).orElse(null);
        if (!authority.getAuthority().equals(role.name())) {
            throw new UserNotAuthorizedException("User Not Authoritzed");
        }

        return new Token(jwtUtil.generateToken(username));
    }
    
}
