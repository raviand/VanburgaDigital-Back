package com.cdn.vanburga.model.response;

import java.util.List;

import com.cdn.vanburga.model.Address;
import com.cdn.vanburga.model.Client;
import com.cdn.vanburga.model.Order;

import lombok.Data;

@Data
public class OrderResponse extends BaseResponse{

	
	private Client client;
	
	private Address address;
	
	private Order order;
	
	private List<ProductData> orderDetail;
	
}
