package com.cdn.vanburga.controller.routes.v1;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdn.vanburga.controller.routes.MenuControllerInterface;
import com.cdn.vanburga.model.State;
import com.cdn.vanburga.model.request.OrderRequest;
import com.cdn.vanburga.model.response.CategoryResponse;
import com.cdn.vanburga.model.response.OrderResponse;
import com.cdn.vanburga.model.response.ProductResponse;
import com.cdn.vanburga.service.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Controller
@Tag(name = "menu", description = "the Menu API")
public class MenuController implements MenuControllerInterface{

	@Autowired
	private MenuService menuService;
	
	
	public ResponseEntity<List<State>> getState(HttpServletRequest request){

		
		return ResponseEntity.status(HttpStatus.OK).body(menuService.getStateList());
	}
	
	@Operation(summary = "Get all Categories", description = "Obtiene todas las categorias cargadas en la base", tags = { "menu" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", 
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))) })	
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
	
	@Override
	public ResponseEntity<ProductResponse> getProduct(@RequestParam(required = true) Long productId,HttpServletRequest request){
		
		ProductResponse productResponse = new ProductResponse();
		
		HttpStatus status = menuService.getProduct(productResponse, productId);
		
		return ResponseEntity.status(status).body(productResponse);
	}
	
	@Override
	public ResponseEntity<OrderResponse> createOrder(OrderRequest orderRequest, HttpServletRequest httpRequest){
		
		OrderResponse orderResponse = new OrderResponse();
		
		HttpStatus status = menuService.createOrder(orderRequest, orderResponse);
		
		return ResponseEntity.status(status).body(orderResponse);
		
	}

}
