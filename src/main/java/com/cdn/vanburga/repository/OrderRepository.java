package com.cdn.vanburga.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdn.vanburga.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	Optional<Order> findById(Long id);
	
	@Query(" select o from Order o where o.createDate between ?1 and ?2 "
			+ " and (?3 = NULL or o.status = ?3 )"
			+ " and (?4 = NULL or o.client.id = ?4 )")
	Optional<List<Order>> findByParameters(LocalDateTime dateFrom,LocalDateTime dateTo,String status,Long clientId);
	
}
