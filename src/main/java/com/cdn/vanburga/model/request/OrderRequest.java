package com.cdn.vanburga.model.request;

import java.math.BigDecimal;
import java.util.List;
import com.cdn.vanburga.model.State;
import lombok.Data;

public class OrderRequest {
	
	private ClientReq client;

	private List<ProductReq> products;

	@Data
	public class ClientReq{
	    String name;
	    String cellphone;
	    String lastName;
	    String mail;
	    
	    AddressReq address;

	    public class AddressReq{
	    	String street;
	    	String doorNumber;
	    	String zipCode;
	    	String floor;
	    	String door;
	    	
	    	StateReq state;
	    	
	    	public class StateReq{
	    		Long id;
	    	}
	    }
	}

	@Data
	public class ProductReq {
		Long id;
		String name;
		
		List<Long> extras;
		
	}
}
