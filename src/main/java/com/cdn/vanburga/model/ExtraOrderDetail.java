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

import lombok.Data;

@Data
@Entity
public class ExtraOrderDetail {

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
    @JoinColumn(name = "idOrderDetail", referencedColumnName = "id")
	private OrderDetail orderDetail;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    @JoinColumn(name = "idExtra", referencedColumnName = "id")
	private Extra extra;
}
