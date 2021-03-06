package com.cdn.vanburga.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

import lombok.Data;

@Data
@Entity
public class Product {

	@Id
	private Long id;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "idCategory", referencedColumnName = "id")
	private Category category;
	
	private BigDecimal price;
	
	private String description;

	private String code;
	
	private Boolean available;
	
	private Product() {}
	
	public Product(Long id) {
		this.id = id;
	}
}
