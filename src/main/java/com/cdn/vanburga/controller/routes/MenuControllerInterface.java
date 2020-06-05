package com.cdn.vanburga.controller.routes;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdn.vanburga.model.response.CategoryResponse;
import com.cdn.vanburga.model.response.ProductResponse;

@Controller
public interface MenuControllerInterface {

	@GetMapping(path = "/category", produces = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<CategoryResponse> getCategory(HttpServletRequest request);
	
	@GetMapping(path = "/product", consumes = (MediaType.APPLICATION_JSON_VALUE), produces = (MediaType.APPLICATION_JSON_VALUE))
    public ResponseEntity<ProductResponse> getProducts(@RequestParam(required = true) Long categoryId,HttpServletRequest request);
}
