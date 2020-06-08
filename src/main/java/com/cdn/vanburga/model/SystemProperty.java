package com.cdn.vanburga.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SystemProperty {

	@Id
	private Integer id;
	
	private String propertyKey;
	
	private String name;
	
	private String oldValue;
	
	private String newValue;
	
	private String description;
	
}
