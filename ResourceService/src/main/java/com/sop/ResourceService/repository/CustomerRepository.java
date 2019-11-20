package com.sop.ResourceService.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sop.ResourceService.model.Customer;

public interface CustomerRepository  extends JpaRepository<Customer,Integer> {
}
