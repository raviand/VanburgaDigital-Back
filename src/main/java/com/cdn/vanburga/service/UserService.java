package com.cdn.vanburga.service;

import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cdn.vanburga.constant.ResponseCode;
import com.cdn.vanburga.model.User;
import com.cdn.vanburga.model.response.UserResponse;
import com.cdn.vanburga.repository.UserRepository;

@Service
public class UserService {

	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired @Lazy
	private UserRepository userRepository;
	
	public HttpStatus registerUser(User user, UserResponse userResponse) {
		
		
		//verifico que el usuario este registrado
		if(user.getLoginId() != null) {
			logger.info("Searching user with Id " + user.getLoginId());
			//aca se registro con Google o Facebook
			Optional<User> users = userRepository.findByLoginId(user.getLoginId());
			if(users.isPresent()) {
				//El usuario ya se encuentra registrado
				userResponse.setCode(ResponseCode.FOUND.fieldNumber());
				userResponse.setMessage(ResponseCode.FOUND.fieldName());
				userResponse.setStatus(HttpStatus.OK.value());
				userResponse.setUser(users.get());
				return HttpStatus.OK;
			}
		}else {
			logger.info("Searching user with email " + user.getEmail());
			Optional<List<User>> users = userRepository.findByEmail(user.getEmail());
			if(users.isPresent() && !users.get().isEmpty()) {
				//Este mail ya esta registrado. Si es un registro lo reboto porque ya lo tengo registrado 
				//sino verifico si tengo el resto de los datos
				if(user.getName() != null) {
					//ERROR!!!! el mail ingresado ya se encuentra registrado
					userResponse.setCode(ResponseCode.USER_MAIL_ALREADY_REGISTERED.fieldNumber());
					userResponse.setMessage(ResponseCode.USER_MAIL_ALREADY_REGISTERED.fieldName());
					userResponse.setStatus(HttpStatus.BAD_REQUEST.value());
					return HttpStatus.BAD_REQUEST;
				}
				Iterator<User> userIterator = users.get().iterator();
				while(userIterator.hasNext()) {
					User u = userIterator.next();
					if(u.getPassword() != null) {
						String registerPassword = new String( Base64.getDecoder().decode(u.getPassword()));
						String enteredPassword = user.getPassword() != null ? new String( Base64.getDecoder().decode(user.getPassword())) : null;
						if(!registerPassword.equals(enteredPassword)) {
							//ERROR en Password, no son iguales
							userResponse.setCode(ResponseCode.LOGIN_ERROR.fieldNumber());
							userResponse.setMessage(ResponseCode.LOGIN_ERROR.fieldName());
							userResponse.setStatus(HttpStatus.BAD_REQUEST.value());
							
							return HttpStatus.BAD_REQUEST;
						}else {
							//OK El password y el mail fueron validados, es un usuario registrado
							userResponse.setCode(ResponseCode.FOUND.fieldNumber());
							userResponse.setMessage(ResponseCode.FOUND.fieldName());
							userResponse.setStatus(HttpStatus.OK.value());
							userResponse.setUser(u);
							return HttpStatus.OK;
						}
					}
				}
			}
			if(user.getName() == null) {
				//ERROR!!!! el mail no esta registrado y no es un alta
				userResponse.setCode(ResponseCode.LOGIN_ERROR.fieldNumber());
				userResponse.setMessage(ResponseCode.LOGIN_ERROR.fieldName());
				userResponse.setStatus(HttpStatus.BAD_REQUEST.value());
				return HttpStatus.BAD_REQUEST;
			}
		}
		
		user = userRepository.save(user);
		userResponse.setCode(ResponseCode.CREATED.fieldNumber());
		userResponse.setMessage(ResponseCode.CREATED.fieldName());
		userResponse.setStatus(HttpStatus.CREATED.value());
		userResponse.setUser(user);
		return HttpStatus.CREATED;
		
	}
	
}
