package com.cdn.vanburga.model.response;

import java.util.List;

import com.cdn.vanburga.model.Extra;
import com.cdn.vanburga.model.Product;

import lombok.Data;

@Data
public class ProductData {

	private Product product;
	
	private List<Extra> extras;
	
}
