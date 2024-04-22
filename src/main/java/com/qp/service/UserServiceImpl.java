package com.qp.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
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

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public UserResponse saveUser(UserRequest userRequest) {
		if (userRequest.getUsername() == null) {
			throw new RuntimeException("Username not found in request..!!");
		} else if (userRequest.getPassword() == null) {
			throw new RuntimeException("Password not found in request..!!");
		}

		User savedUser = null;

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = userRequest.getPassword();
		String encodedPassword = encoder.encode(rawPassword);

		User user = modelMapper.map(userRequest, User.class);
		user.setPassword(encodedPassword);
		if (userRequest.getId() != null) {
		    Optional<User> optionalUser = userRepository.findById(userRequest.getId());
		    if (optionalUser.isPresent()) {
		        User oldUser = optionalUser.get();
		        oldUser.setPassword(user.getPassword());
		        oldUser.setUsername(user.getUsername());
		        oldUser.setRoles(user.getRoles());

		        savedUser = userRepository.save(oldUser);
		    } else {
				throw new RuntimeException("Can't find record: " + userRequest.getId());
			}
		} else {           
			savedUser = userRepository.save(user);
		}
		UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
		return userResponse;
	    
	}

}