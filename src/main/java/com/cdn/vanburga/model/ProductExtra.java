package com.cdn.vanburga.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "ProductByExtra")
public class ProductExtra {

	@EmbeddedId
	private ProductExtraIdentity productExtra;
	
}
