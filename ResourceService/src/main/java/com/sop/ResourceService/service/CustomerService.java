package com.sop.ResourceService.service;

import java.util.List;

import com.sop.ResourceService.model.Customer;

public interface CustomerService {


    Customer save(Customer customer);

    Customer fetchById(int profileId);

    List<Customer> fetchAllProfiles();
}
