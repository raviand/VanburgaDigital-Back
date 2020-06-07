package com.cdn.vanburga.model.response;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class BaseResponse {

	private String message;
	
	private Integer code;
	
	private HttpStatus status;
	
}
