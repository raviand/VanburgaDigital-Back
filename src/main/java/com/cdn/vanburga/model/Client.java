package com.cdn.vanburga.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Client {

	@Id
	private Long id;
	
	private String name;
	
	private String lastName;
	
	private String cellphone;
	
	private String mail;
	
}
