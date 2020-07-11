package com.cdn.vanburga.controller.routes.v1;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdn.vanburga.controller.routes.MenuControllerInterface;
import com.cdn.vanburga.model.Extra;
import com.cdn.vanburga.model.State;
import com.cdn.vanburga.model.User;
import com.cdn.vanburga.model.request.OrderRequest;
import com.cdn.vanburga.model.response.CategoryResponse;
import com.cdn.vanburga.model.response.KitchenResponse;
import com.cdn.vanburga.model.response.OrderResponse;
import com.cdn.vanburga.model.response.ProductResponse;
import com.cdn.vanburga.model.response.UserResponse;
import com.cdn.vanburga.service.MenuService;
import com.cdn.vanburga.service.UserService;
import com.cdn.vanburga.util.ServiceUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Tag(name = "menu", description = "the Menu API")
public class MenuController implements MenuControllerInterface{

	private static final Logger logger = LogManager.getLogger(MenuController.class);
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserService userService;
	
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
	public ResponseEntity<KitchenResponse> getKitchenStatus(HttpServletRequest request){
		
		KitchenResponse kitchenResponse = new KitchenResponse();
		
		HttpStatus status = menuService.getKitchen(kitchenResponse);
		
		return ResponseEntity.status(status).body(kitchenResponse);
	}
	
	@Override
	public ResponseEntity<ProductResponse> getProduct(@RequestParam(required = true) Long productId,HttpServletRequest request){
		
		ProductResponse productResponse = new ProductResponse();
		
		HttpStatus status = menuService.getProduct(productResponse, productId);
		
		return ResponseEntity.status(status).body(productResponse);
	}
	
	@Override
	public ResponseEntity<OrderResponse> createOrder(OrderRequest orderRequest, HttpServletRequest httpRequest){
		
		try {
			OrderResponse orderResponse = new OrderResponse();
			ObjectMapper Obj = new ObjectMapper();
			logger.info("request: " + Obj.writerWithDefaultPrettyPrinter().writeValueAsString(orderRequest));
		
			HttpStatus status = menuService.createOrder(orderRequest, orderResponse);
			
			logger.info("response: " + ServiceUtils.objectToJson(orderResponse));
			return ResponseEntity.status(status).body(orderResponse);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}

	@Override
	public ResponseEntity<OrderResponse> getOrder(Long orderId, HttpServletRequest request) {

		OrderResponse orderResponse = new OrderResponse();
		
		HttpStatus status = menuService.getOrder(orderId, orderResponse);
		
		return ResponseEntity.status(status).body(orderResponse);
	}
	
	@Override
	public ResponseEntity<OrderResponse> updateOrder(OrderRequest orderRequest, HttpServletRequest request) {//Es necesario que llegue orderrequest?

		logger.info("request: " + ServiceUtils.objectToJson(orderRequest));
		OrderResponse orderResponse = new OrderResponse(); 
		
		HttpStatus status = menuService.updateOrder(orderRequest, orderResponse);
		logger.info("response: " + ServiceUtils.objectToJson(orderResponse));
		return ResponseEntity.status(status).body(orderResponse);
	}
	
	@Override
	public ResponseEntity<OrderResponse> cancelOrder(OrderRequest orderRequest, HttpServletRequest request) {//Es necesario que llegue orderrequest?

		logger.info("request: " + ServiceUtils.objectToJson(orderRequest));
		OrderResponse orderResponse = new OrderResponse(); 
		
		HttpStatus status = menuService.cancelOrder(orderRequest, orderResponse);
		logger.info("response: " + ServiceUtils.objectToJson(orderResponse));
		return ResponseEntity.status(status).body(orderResponse);
	}
	
	public ResponseEntity<OrderResponse> findOrder(
    		String status,
    		String dateFrom,
    		String dateTo,
    		String clientId,
    		String name,
    		String orderId,
    		String state,
    		String categoryId,
    		HttpServletRequest request){
		
		OrderResponse orderResponse = new OrderResponse();
				
		HttpStatus httpStatus = menuService.findOrders(
				status,
	    		dateFrom,
	    		dateTo,
	    		clientId,
	    		name,
	    		orderId,
	    		state,
	    		categoryId, orderResponse);
		
		return ResponseEntity.status(httpStatus).body(orderResponse);
	
	}
	
	public ResponseEntity<UserResponse> createUser(User user, HttpServletRequest httpRequest){
		
		
		logger.info("request: " + ServiceUtils.objectToJson(user));
		UserResponse userResponse = new UserResponse();
		HttpStatus status = userService.registerUser(user, userResponse);
		
		logger.info("response: " + ServiceUtils.objectToJson(userResponse));
		return ResponseEntity.status(status).body(userResponse);
		
		
	}
	
	/**
	 * Busca todos los extras creados
	 */
	public ResponseEntity<List<Extra>> getExtras(HttpServletRequest request){
		return ResponseEntity.status(HttpStatus.OK).body(menuService.getExtras());
	}

}
