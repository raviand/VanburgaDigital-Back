package com.cdn.vanburga.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdn.vanburga.model.Category;
import com.cdn.vanburga.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<List<Product>> findByCategory(Category category);
}
