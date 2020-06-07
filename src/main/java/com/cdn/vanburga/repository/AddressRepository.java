package com.cdn.vanburga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdn.vanburga.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
