package com.cdn.vanburga.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class BusinessSchedule {

	@Id
	private Long id;
	private String day;
	private String openTime;
	private String closeTime;
	private Boolean available;
	
	
}
