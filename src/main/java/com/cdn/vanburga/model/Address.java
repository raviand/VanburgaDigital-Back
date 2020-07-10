package com.cdn.vanburga.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Address {

	@Id
	@GeneratedValue(
	        strategy= GenerationType.AUTO,
	        generator="native"
	)
	@GenericGenerator(
	        name = "native",
	        strategy = "native")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idClient", referencedColumnName = "id")
	@JsonIgnore
	private Client client;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idState", referencedColumnName = "id")
	private State state;
	
	private String street;
	
	private String doorNumber;
	
	private String reference;
	
	private String floor;
	
	private String door;
	
}
