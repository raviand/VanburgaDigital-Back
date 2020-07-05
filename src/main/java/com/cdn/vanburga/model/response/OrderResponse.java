package com.cdn.vanburga.model.response;

import java.util.List;

import com.cdn.vanburga.model.Address;
import com.cdn.vanburga.model.Order;
import com.cdn.vanburga.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(Include.NON_NULL)
public class OrderResponse extends BaseResponse{

	
	@JsonIgnore
	private Address address;

	private Order order;
	@JsonIgnore
	private List<Product> products;
	
	private List<Order> orders;
	
}
