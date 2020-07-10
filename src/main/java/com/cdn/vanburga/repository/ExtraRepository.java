package com.cdn.vanburga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdn.vanburga.model.Extra;

public interface ExtraRepository extends JpaRepository<Extra, Long>{

	List<Extra> findAllByOrderByCode();
	
}
