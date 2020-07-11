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
public class KitchenResponse extends BaseResponse {
	
	private Integer chips;
	
	private Integer grilledHamburger;
	
	private Integer simpleCheddar;
	
	private Integer doubleCheddar;
	
	private Integer tripleCheddar;
	
	private Integer simpleEmmenthal;
	
	private Integer doubleEmmenthal;
	
	private Integer tripleEmmenthal;
	
	private Integer noCheese;
	
	private Integer orderCount;
	
	private Integer productCount;
	
	private List<Order> orders;

}
