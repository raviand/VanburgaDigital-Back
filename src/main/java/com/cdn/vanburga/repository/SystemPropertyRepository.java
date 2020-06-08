package com.cdn.vanburga.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdn.vanburga.model.SystemProperty;


public interface SystemPropertyRepository extends JpaRepository<SystemProperty, Integer> {

 	public Optional<List<SystemProperty>> findByPropertyKeyLike(String propertyKey);

 	public Optional<SystemProperty> findByPropertyKey(String propertyKey);
	
}
