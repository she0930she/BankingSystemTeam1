package com.learning.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.learning.exception.WrongURLException;

@Component
public class JwtVerify {
	
	private final static String HEADER = "Authorization";
	private final static String PREFIX = "Bearer ";
	private static JwtUtil jwtUtil;

	@Autowired
	public JwtVerify(JwtUtil jwtUtil) {
		super();
		JwtVerify.jwtUtil = jwtUtil;
	}

	public static void jwtVerify(HttpServletRequest httpServletRequest, String username) {
		
		final String authorizationHeader = httpServletRequest.getHeader(HEADER);
		String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith(PREFIX)) {
            jwt = authorizationHeader.substring(7);
        }
        String username2 = jwtUtil.extractUsername(jwt);
        if(!username.equals(username2)) {
        	throw new WrongURLException(username2 + " does not have access to " + username);
        }
		

	}

}
