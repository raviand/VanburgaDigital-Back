package com.cdn.vanburga.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Extra {

	@Id
	private Long id;
	
	private String name;
	
	private String code;
	
	private BigDecimal price;
	
	private Boolean available;
	
}
