package com.cdn.vanburga.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "`Order`")
public class Order {

	@Id
	@GeneratedValue(
	        strategy= GenerationType.AUTO,
	        generator="native"
	)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    @JoinColumn(name = "idClient", referencedColumnName = "id")
	private Client client;
	
	private String comments;
	
	private Boolean delivery;

	private String status;
	
	private LocalDateTime createDate;
	
	private BigDecimal amount;
	
}
