package com.cdn.vanburga.model.response;

import com.cdn.vanburga.model.User;

import lombok.Data;

@Data
public class UserResponse extends BaseResponse{

	private User user;
	
}
