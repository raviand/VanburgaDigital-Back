package com.cdn.vanburga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdn.vanburga.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
