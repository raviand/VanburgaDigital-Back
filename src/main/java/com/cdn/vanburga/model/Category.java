package com.cdn.vanburga.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Category {

	@Id
	private Long id;
	
	private String name;
	
	private String description;
	
	public Category(Long id) {
		this.id=id;
	}
	
	public Category () {}
}
