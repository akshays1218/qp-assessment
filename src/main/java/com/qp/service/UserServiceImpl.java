package com.qp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.qp.entity.User;
import com.qp.repository.UserRepository;
import com.qp.request.UserRequest;
import com.qp.response.UserResponse;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;


	@Override
	public UserResponse saveUser(UserRequest userRequest) {
	    // Check if username and password are provided
	    if (userRequest.getUsername() == null) {
	        throw new RuntimeException("Parameter username is not found in request..!!");
	    } else if (userRequest.getPassword() == null) {
	        throw new RuntimeException("Parameter password is not found in request..!!");
	    }

	    // Encode the password
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    String encodedPassword = encoder.encode(userRequest.getPassword());

	    // Create or update user
	    User savedUser;
	    if (userRequest.getId() != null) {
	        // Update existing user
	        Optional<User> optionalUser = userRepository.findById(userRequest.getId());
	        if (optionalUser.isPresent()) {
	            User oldUser = optionalUser.get();
	            oldUser.setPassword(encodedPassword);
	            oldUser.setUsername(userRequest.getUsername());
	            oldUser.setRoles(userRequest.getRoles()); // Assuming this updates user roles
	            savedUser = userRepository.save(oldUser);
	        } else {
	            throw new RuntimeException("Can't find record with identifier: " + userRequest.getId());
	        }
	    } else {
	        // Create new user
	        User newUser = new User();
	        newUser.setUsername(userRequest.getUsername());
	        newUser.setPassword(encodedPassword);
	        newUser.setRoles(userRequest.getRoles());
	        savedUser = userRepository.save(newUser);
	    }

	    
	    UserResponse userResponse = new UserResponse(); // You need to implement this part
	   

	    return userResponse;
	}

}
