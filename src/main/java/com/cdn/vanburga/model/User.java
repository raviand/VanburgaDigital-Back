package com.cdn.vanburga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	private String loginId;
	private String name;
	private String email;
	private String providerId;
	private String provider;
	
	@Column(name = "imageUrl")
	private String image;
	private String token;
	private String idToken;
	
}
