package com.qp.request;

import java.util.Set;

import com.qp.entity.UserRole;

public class UserRequest {

    private Long id;
    private String username;
    private String password;
    private Set<UserRole> roles;
    
    
	public UserRequest() {
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Set<UserRole> getRoles() {
		return roles;
	}


	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}


    
    
}
