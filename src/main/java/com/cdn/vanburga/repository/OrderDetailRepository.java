package com.cdn.vanburga.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdn.vanburga.model.Order;
import com.cdn.vanburga.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{

	Optional<List<OrderDetail>> findByOrder(Order order);
	
}
