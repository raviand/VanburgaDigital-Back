package com.cdn.vanburga.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class MenuRecipe {
	
	@Id
	private Long id;

	private String code;
	
	private Integer idRecipe;
	
	private Integer quantity;

}
