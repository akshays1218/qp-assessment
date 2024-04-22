package com.qp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.qp.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

	User findByUsername(String username);

	Optional<User> findById(Long id);

}
