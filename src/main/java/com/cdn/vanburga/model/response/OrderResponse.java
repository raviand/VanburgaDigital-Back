package com.cdn.vanburga.model.response;

import java.util.List;

import com.cdn.vanburga.model.Address;
import com.cdn.vanburga.model.Client;
import com.cdn.vanburga.model.Order;
import com.cdn.vanburga.model.Product;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderResponse extends BaseResponse{

	
	
	private Address address;
	
	private Order order;
	
	private String whatsappLink;
	
	private List<Product> orderDetail;
	
	private List<Order> orders;
	
}
