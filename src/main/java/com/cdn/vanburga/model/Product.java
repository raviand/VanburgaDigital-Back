package com.cdn.vanburga.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class Product implements Cloneable{

	@Id
	private Long id;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "idCategory", referencedColumnName = "id")
	private Category category;
	
	private BigDecimal price;
	
	private String description;

	private String code;
	
	private String color;
	
	private Boolean available;
	
	private Integer rawMaterial;
	
	@Transient
	private List<Extra> extras;
	
	
	private Product() {}
	
	public Product(Long id) {
		this.id = id;
	}
	
	public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
}
