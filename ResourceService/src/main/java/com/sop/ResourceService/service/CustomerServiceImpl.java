package com.sop.ResourceService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sop.ResourceService.model.Customer;
import com.sop.ResourceService.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer fetchById(int profileId) {
		Optional<Customer> customer = customerRepository.findById(profileId);
		if (customer.isPresent()) {
			return customer.get();
		} else {
			return null;
		}
	}

	@Override
	public List<Customer> fetchAllProfiles() {
		return customerRepository.findAll();
	}
}
