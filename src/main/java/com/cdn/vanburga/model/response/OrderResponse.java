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
	@JsonIgnore
	private Order order;
	@JsonIgnore
	private List<Product> prodcts;
	
	private List<Order> orders;
	
	
	
	@Data
	public class OrderDetail{
		
		private Order order;
		
		private List<Product> prodcts;
		
	}
	
	public OrderDetail getOrderDetail() {
		if(this.order == null) return null;
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrder(this.order);
		orderDetail.getOrder().getClient().setAddress(this.address);
		orderDetail.setProdcts(this.prodcts);
		return orderDetail;
		
	}
}
