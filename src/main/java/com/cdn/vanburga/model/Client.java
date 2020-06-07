package com.cdn.vanburga.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity
public class Client {
	
	@Schema(description = "Unique identifier of the Contact.", 
            example = "1", required = true)
	@Id
	@GeneratedValue(
	        strategy= GenerationType.AUTO,
	        generator="native"
	)
	@GenericGenerator(
	        name = "native",
	        strategy = "native")
	private Long id;
	
	private String name;
	
	private String lastName;
	
	private String cellphone;
	
	private String mail;
	
}
