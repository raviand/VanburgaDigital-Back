package com.cdn.vanburga.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "`Order`")
@JsonInclude(Include.NON_NULL)
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

	private String status;
	
	private LocalDateTime createDate;
	
	private String whatsappLink;
	
	private BigDecimal amount;
	
	private Boolean delivery;
	
	private String deliverTime;
	
	private String paymentType;
	
	@Transient
	private List<Product> products;
	
}
