package com.sop.AuthorizationService.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sop.AuthorizationService.model.Customer;

@Repository
public interface UserRepository extends CrudRepository<Customer, Integer> {
//	@Query("SELECT t.email FROM Customer t where t.email = :email")
	Customer findByEmail(String email);
}
