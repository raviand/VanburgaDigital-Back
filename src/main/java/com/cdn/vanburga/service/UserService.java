package com.cdn.vanburga.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cdn.vanburga.model.User;
import com.cdn.vanburga.repository.UserRepository;

@Service
public class UserService {

	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired @Lazy
	private UserRepository userRepository;
	
	public HttpStatus registerUser(User user) {
		
		logger.info("Register user " + user.getName());
		
		userRepository.save(user);
		
		return HttpStatus.OK;
		
	}
	
}
