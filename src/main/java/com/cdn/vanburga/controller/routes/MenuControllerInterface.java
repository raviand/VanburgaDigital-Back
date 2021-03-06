package com.cdn.vanburga.controller.routes;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cdn.vanburga.model.State;
import com.cdn.vanburga.model.request.OrderRequest;
import com.cdn.vanburga.model.response.CategoryResponse;
import com.cdn.vanburga.model.response.OrderResponse;
import com.cdn.vanburga.model.response.ProductResponse;



@Controller
@CrossOrigin(origins = "*")
public interface MenuControllerInterface {


	/**********************************************************************
	 * endpoints de combos
	 * ********************************************************************/
	@GetMapping(path = "/state", produces = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<List<State>> getState(HttpServletRequest request);
	
	@GetMapping(path = "/category", produces = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<CategoryResponse> getCategory(HttpServletRequest request);
	
	/**********************************************************************
	 * endpoints de Producto
	 * ********************************************************************/
	@GetMapping(path = "/product/search", consumes = (MediaType.APPLICATION_JSON_VALUE), produces = (MediaType.APPLICATION_JSON_VALUE))
    public ResponseEntity<ProductResponse> getProducts(@RequestParam(required = true) Long categoryId,HttpServletRequest request);
	
	@GetMapping(path = "/product", consumes = (MediaType.APPLICATION_JSON_VALUE), produces = (MediaType.APPLICATION_JSON_VALUE))
    public ResponseEntity<ProductResponse> getProduct(@RequestParam(required = true) Long productId,HttpServletRequest request);
	
	/**********************************************************************
	 * endpoints de Orden
	 * ********************************************************************/
	@PostMapping(path = "/order", consumes = (MediaType.APPLICATION_JSON_VALUE), produces = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest httpRequest); 
	
	@GetMapping(path = "/order", consumes = (MediaType.APPLICATION_JSON_VALUE), produces = (MediaType.APPLICATION_JSON_VALUE))
    public ResponseEntity<OrderResponse> getOrder(@RequestParam(required = true) Long orderId,HttpServletRequest request);
	
	@PutMapping(path = "/order", consumes = (MediaType.APPLICATION_JSON_VALUE), produces = (MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest httpRequest); 
}
