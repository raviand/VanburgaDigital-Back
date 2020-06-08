package com.cdn.vanburga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdn.vanburga.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	Optional<Order> findById(Long id);
	
}
