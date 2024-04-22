package com.qp.service;

import org.springframework.stereotype.Service;

import com.qp.request.UserRequest;
import com.qp.response.UserResponse;

@Service
public interface UserService {

	UserResponse saveUser(UserRequest userRequest);
}
