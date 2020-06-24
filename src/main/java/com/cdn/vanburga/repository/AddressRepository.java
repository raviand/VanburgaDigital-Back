package com.cdn.vanburga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdn.vanburga.model.Address;
import com.cdn.vanburga.model.Client;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
	Optional<Address> findByClient(Client client);
	
}
