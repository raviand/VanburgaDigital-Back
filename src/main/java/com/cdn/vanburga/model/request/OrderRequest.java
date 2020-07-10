package com.cdn.vanburga.model.request;

import java.util.List;

import com.cdn.vanburga.model.Product;
import com.cdn.vanburga.model.State;

import lombok.Data;

@Data
public class OrderRequest {
	
	private ClientReq client;

	private List<Product> products;

	private String comment;
	
	private Long orderId;
	
	private String status;
	
	private Boolean delivery;
	
	private String deliverTime;
	
	private String paymentType;
	
	@Data
	public class ClientReq{
		private String name;
		private String cellphone;
		private String lastName;
		private String mail;
	    
		private AddressReq address;

	    @Data
	    public class AddressReq{
	    	private String street;
	    	private String doorNumber;
	    	private String reference;
	    	private String floor;
	    	private String door;
	    	private State state;
	    }
	}

}
