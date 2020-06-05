package com.cdn.vanburga.controller.routes;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cdn.vanburga.model.response.CategoryResponse;

@Controller
public interface MenuControllerInterface {

	@GetMapping(path = "/category", produces = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<CategoryResponse> getCategory();

	
}
