package com.cdn.vanburga.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdn.vanburga.model.Product;
import com.cdn.vanburga.model.ProductExtra;

public interface ProductExtraRepository extends JpaRepository<ProductExtra, Long> {

	@Query(value = "select pe from ProductExtra pe where pe.productExtra.product.id = ?1")
	Optional<List<ProductExtra>> findByProductExtraProduct(Long productId);
	
}
