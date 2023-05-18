package com.learning.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learning.entity.Token;
import com.learning.entity.User;
import com.learning.entity.UserRole;
import com.learning.service.AuthenticationService;


@CrossOrigin
@RestController
@RequestMapping("/api/authenticate")
public class AuthenticationController {
	
	private AuthenticationService authenticationService;
	
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/customer")
    public Token authenticateCustomer(@RequestBody User user) {
        return authenticationService.authenticate(user, UserRole.CUSTOMER);
    }

    @PostMapping("/admin")
    public Token authenticateSuperAdmin(@RequestBody User user){
        return authenticationService.authenticate(user, UserRole.SUPER_ADMIN);
    }
    
    @PostMapping("/staff")
    public Token authenticateStaff(@RequestBody User user){
        return authenticationService.authenticate(user, UserRole.STAFF);
    }
}
