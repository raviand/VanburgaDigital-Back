package com.cdn.vanburga.model.response;

import java.util.List;

import com.cdn.vanburga.model.Product;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductResponse extends BaseResponse{
	
	private List<Product> products;
}
