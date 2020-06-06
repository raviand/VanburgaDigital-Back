package com.cdn.vanburga.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable
public class ProductExtraIdentity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 46592465213162606L;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProduct", referencedColumnName = "id")
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idExtra", referencedColumnName = "id")
	private Extra extra;
	
}
