package com.cdn.vanburga.model.response;

import lombok.Data;

@Data
public class BaseResponse {

	private String message;
	
	private Integer code;
	
	private Integer status;
	
}
