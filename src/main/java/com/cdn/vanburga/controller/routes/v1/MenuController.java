package com.cdn.vanburga.controller.routes.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.cdn.vanburga.controller.routes.MenuControllerInterface;
import com.cdn.vanburga.model.response.CategoryResponse;
import com.cdn.vanburga.service.MenuService;

@Controller
public class MenuController implements MenuControllerInterface{

	@Autowired
	private MenuService menuService;
	
	@Override
	public ResponseEntity<CategoryResponse> getCategory() {
		
		CategoryResponse categoryResponse = new CategoryResponse();
		
		HttpStatus status = menuService.getAllCategories(categoryResponse);
		
		return ResponseEntity.status(status).body(categoryResponse);
	}

}
