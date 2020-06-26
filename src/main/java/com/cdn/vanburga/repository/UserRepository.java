package com.cdn.vanburga.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdn.vanburga.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByLoginId(String loginId);
	
	Optional<List<User>> findByEmail(String email);
	
}
