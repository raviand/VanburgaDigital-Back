package com.cdn.vanburga.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Product {

	@Id
	private Long id;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCategory", referencedColumnName = "id")
	private Category category;
	
	private BigDecimal price;
	
	private String description;
	
	private Boolean available;
}
