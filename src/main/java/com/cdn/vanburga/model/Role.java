package com.cdn.vanburga.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Role {

	@Id
	private Long id;
	private String role;
}
