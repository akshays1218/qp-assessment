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
	    if (userRequest.getUsername() == null) {
	        throw new RuntimeException("username is not found in request..!!");
	    } else if (userRequest.getPassword() == null) {
	        throw new RuntimeException("password is not found in request..!!");
	    }
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    String encodedPassword = encoder.encode(userRequest.getPassword());

	    User savedUser;
	    if (userRequest.getId() != null) {
	        Optional<User> optionalUser = userRepository.findById(userRequest.getId());
	        if (optionalUser.isPresent()) {
	            User oldUser = optionalUser.get();
	            oldUser.setPassword(encodedPassword);
	            oldUser.setUsername(userRequest.getUsername());
	            oldUser.setRoles(userRequest.getRoles()); 
	            savedUser = userRepository.save(oldUser);
	        } else {
	            throw new RuntimeException("Can't find record: " + userRequest.getId());
	        }
	    } else {
	       
	        User newUser = new User();
	        newUser.setUsername(userRequest.getUsername());
	        newUser.setPassword(encodedPassword);
	        newUser.setRoles(userRequest.getRoles());
	        savedUser = userRepository.save(newUser);
	    }	    
	    UserResponse userResponse = new UserResponse(); 
	    return userResponse;
	}

}
