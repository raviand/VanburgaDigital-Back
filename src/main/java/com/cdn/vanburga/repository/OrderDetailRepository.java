package com.cdn.vanburga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdn.vanburga.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{

}
