package com.qp.response;

import java.util.Set;

import com.qp.entity.UserRole;

public class UserResponse {

	private Long id;
	private String username;
	private Set<UserRole> roles;

	public UserResponse() {
		super();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

}
