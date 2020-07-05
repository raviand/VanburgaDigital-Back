package com.cdn.vanburga.model.response;

import com.cdn.vanburga.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UserResponse extends BaseResponse{

	private User user;
	
}
