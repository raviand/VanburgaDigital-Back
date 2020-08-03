package com.cdn.vanburga.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class Extra implements Cloneable{

	@Id
	private Long id;
	
	private String name;
	
	private String code;
	
	private BigDecimal price;
	
	private Integer rawMaterial;
	
	private Integer quantityLimit;
	
	@Transient
	private Integer quantity;
	
	private Boolean available;
	
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
