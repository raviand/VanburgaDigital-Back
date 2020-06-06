package com.cdn.vanburga.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cdn.vanburga.model.Category;
import com.cdn.vanburga.model.Extra;
import com.cdn.vanburga.model.Product;
import com.cdn.vanburga.model.ProductExtra;
import com.cdn.vanburga.model.response.CategoryResponse;
import com.cdn.vanburga.model.response.ProductData;
import com.cdn.vanburga.model.response.ProductResponse;
import com.cdn.vanburga.repository.CategoryRepository;
import com.cdn.vanburga.repository.ProductExtraRepository;
import com.cdn.vanburga.repository.ProductRepository;

@Service
public class MenuService {

	@Autowired @Lazy
	private CategoryRepository categoryRepository;
	
	@Autowired @Lazy
	private ProductRepository productRepository;
	
	@Autowired @Lazy
	private ProductExtraRepository productExtraRepository;
	
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
	
	
	public HttpStatus getProduct(ProductResponse response, Long id) {
		
		Optional<Product> product = productRepository.findById(id);
		
		if (product.isPresent()) {
			
			Optional<List<ProductExtra>> productList = productExtraRepository.findByProductExtraProduct(product.get().getId());
			ProductData productData = new ProductData();
			productData.setProduct(product.get());
			if(productList.isPresent()) {	
				List<Extra> extras = new ArrayList<Extra>();
				productList.get().stream().forEach(e -> extras.add(e.getProductExtra().getExtra()));
				productData.setExtras(extras);
			}
			response.setProduct(productData);
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
