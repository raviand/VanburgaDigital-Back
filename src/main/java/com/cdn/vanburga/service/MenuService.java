package com.cdn.vanburga.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cdn.vanburga.model.Category;
import com.cdn.vanburga.model.Product;
import com.cdn.vanburga.model.response.CategoryResponse;
import com.cdn.vanburga.model.response.ProductResponse;
import com.cdn.vanburga.repository.CategoryRepository;
import com.cdn.vanburga.repository.ProductRepository;

@Service
public class MenuService {

	@Autowired @Lazy
	private CategoryRepository categoryRepository;
	
	@Autowired @Lazy
	private ProductRepository productRepository;
	
	public HttpStatus getAllCategories(CategoryResponse response) {
	
		List<Category> categoriesList = categoryRepository.findAll();
		response.setCategories(categoriesList);
		response.setStatus(HttpStatus.OK.name());
		response.setCode(HttpStatus.OK.value());
		response.setMessage("Success");
		
		return HttpStatus.OK;
		
	}
	
	
	public HttpStatus getProductsByCategory(ProductResponse response, Long id) {
		
		Optional<List<Product>> productList = productRepository.findByCategory(new Category(id));
		
		if (productList.isPresent()) {
			response.setProducts(productList.get());
			response.setCode(HttpStatus.OK.value());
			response.setStatus(HttpStatus.OK.name());
			response.setMessage("Success");
			return HttpStatus.OK;
		}
		response.setStatus(HttpStatus.NOT_FOUND.name());
		response.setCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("No products found");
		
		return HttpStatus.NOT_FOUND;
		
	}
	
}
