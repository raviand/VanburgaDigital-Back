package com.cdn.vanburga.model.response;

import java.util.List;

import com.cdn.vanburga.model.Category;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CategoryResponse extends BaseResponse{
	
	private List<Category> categories;
	
}
