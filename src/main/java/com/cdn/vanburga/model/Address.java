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
public class Address {

	@Id
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idClient", referencedColumnName = "id")
	private Client client;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idState", referencedColumnName = "id")
	private State state;
	
	private String street;
	
	private String doorNumber;
	
	private String zipCode;
	
	private String floor;
	
	private String door;
	
}
