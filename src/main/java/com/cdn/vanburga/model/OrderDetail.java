package com.cdn.vanburga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class OrderDetail {

	@Id
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProduct", referencedColumnName = "id")
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idOrder", referencedColumnName = "id")
	private Order order;
	
	
}
