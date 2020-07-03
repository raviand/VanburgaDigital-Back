package com.cdn.vanburga.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class State {

	@Id
	private Long id;
	
	private String state;
	
	private BigDecimal amount;
	
}
