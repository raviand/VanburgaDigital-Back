package com.cdn.vanburga.controller.routes.v1;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdn.vanburga.controller.routes.MenuControllerInterface;
import com.cdn.vanburga.model.response.CategoryResponse;
import com.cdn.vanburga.model.response.ProductResponse;
import com.cdn.vanburga.service.MenuService;

@Controller
public class MenuController implements MenuControllerInterface{

	@Autowired
	private MenuService menuService;
	
	@Override
	public ResponseEntity<CategoryResponse> getCategory(HttpServletRequest request) {
		
		CategoryResponse categoryResponse = new CategoryResponse();
		
		HttpStatus status = menuService.getAllCategories(categoryResponse);
		
		return ResponseEntity.status(status).body(categoryResponse);
	}
	
	@Override
	public ResponseEntity<ProductResponse> getProducts(@RequestParam(required = true) Long categoryId,HttpServletRequest request){
		
		ProductResponse productResponse = new ProductResponse();
		
		HttpStatus status = menuService.getProductsByCategory(productResponse, categoryId);
		
		return ResponseEntity.status(status).body(productResponse);
	}

}
