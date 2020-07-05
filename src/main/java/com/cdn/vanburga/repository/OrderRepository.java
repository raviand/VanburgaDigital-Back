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
			+ " and (?4 = NULL or o.client.id = ?4 )"
			+ " and (?5 = NULL or o.client.name like %?5% )"
			+ " and (?6 = NULL or o.id = ?6 )"
			//+ " and (?7 = NULL or o.client.address.state.state = ?7 )"
			)
	Optional<List<Order>> findByParameters( LocalDateTime dateFrom, //1
											LocalDateTime dateTo, //2
											String status, //3
											Long clientId, //4
											String name, //5
											Long order, //6
											String state); //7
	
	
	
}
