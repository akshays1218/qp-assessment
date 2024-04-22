package com.qp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qp.entity.User;
import com.qp.request.AuthRequestDTO;
import com.qp.request.UserRequest;
import com.qp.response.JwtResponseDTO;
import com.qp.response.UserResponse;
import com.qp.service.JwtService;
import com.qp.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserService userService;
	
	
	@PostMapping("/save")
	public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest) {
		try {
			UserResponse userResponse = userService.saveUser(userRequest);
			return ResponseEntity.ok(userResponse);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@PostMapping("/login")
	public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
	    
	    if (authentication.isAuthenticated()) {
	        String token = jwtService.GenerateToken(authRequestDTO.getUsername());
	        JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
	        jwtResponseDTO.setToken(token);
	        return jwtResponseDTO;
	    } else {
	        throw new UsernameNotFoundException("Invalid user request..!!");
	    }
	}


}
