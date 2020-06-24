package com.cdn.vanburga.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdn.vanburga.model.ExtraOrderDetail;
import com.cdn.vanburga.model.Order;

public interface ExtraOrderDetailRepository extends JpaRepository<ExtraOrderDetail, Long>{
	
	@Query("Select eod from ExtraOrderDetail eod where eod.orderDetail.order = ?1")
	Optional<List<ExtraOrderDetail>> findByOrder(Order OrderId);

}
