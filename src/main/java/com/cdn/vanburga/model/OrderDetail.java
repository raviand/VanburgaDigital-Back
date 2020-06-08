package com.cdn.vanburga.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class OrderDetail {

	@Id
	@GeneratedValue(
	        strategy= GenerationType.AUTO,
	        generator="native"
	)
	@GenericGenerator(
	        name = "native",
	        strategy = "native")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    @JoinColumn(name = "idProduct", referencedColumnName = "id")
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    @JoinColumn(name = "idOrder", referencedColumnName = "id")
	@JsonIgnore
	private Order order;
	
	
}
