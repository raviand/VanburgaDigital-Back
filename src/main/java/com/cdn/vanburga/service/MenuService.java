package com.cdn.vanburga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cdn.vanburga.model.Category;
import com.cdn.vanburga.model.response.CategoryResponse;
import com.cdn.vanburga.repository.CategoryRepository;

@Service
public class MenuService {

	@Autowired @Lazy
	private CategoryRepository categoryRepository;
	
	public HttpStatus getAllCategories(CategoryResponse response) {
	
		List<Category> categoriesList = categoryRepository.findAll();
		response.setCategories(categoriesList);
		response.setStatus(HttpStatus.OK.name());
		response.setCode(HttpStatus.OK.value());
		response.setMessage("Success");
		
		return HttpStatus.OK;
		
	}
	
}
