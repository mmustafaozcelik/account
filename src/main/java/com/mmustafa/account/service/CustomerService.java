package com.mmustafa.account.service;

import com.mmustafa.account.exception.CustomerNotFoundException;
import com.mmustafa.account.model.Customer;
import com.mmustafa.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    protected Customer findCustomerById(String id){
        return customerRepository.findById(id).orElseThrow(() ->
                new CustomerNotFoundException("Customer Not Found ById: "+ id));
    }
}
