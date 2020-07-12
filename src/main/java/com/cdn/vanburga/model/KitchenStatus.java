package com.cdn.vanburga.model;

import java.util.List;

import lombok.Data;

@Data
public class KitchenStatus {

	public KitchenStatus () {}
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
