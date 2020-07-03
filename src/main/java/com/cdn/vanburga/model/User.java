package com.cdn.vanburga.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
	private String password;
	private String phone;
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    @JoinColumn(name = "roleId", referencedColumnName = "id")
	private Role role;
	
}
