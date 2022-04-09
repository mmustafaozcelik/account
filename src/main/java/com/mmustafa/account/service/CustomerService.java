package com.mmustafa.account.service;

import com.mmustafa.account.dto.CustomerDto;
import com.mmustafa.account.dto.CustomerDtoConverter;
import com.mmustafa.account.exception.CustomerNotFoundException;
import com.mmustafa.account.model.Customer;
import com.mmustafa.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter converter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter converter) {
        this.customerRepository = customerRepository;
        this.converter = converter;
    }
    protected Customer findCustomerById(String id){
        return customerRepository.findById(id).orElseThrow(() ->
                new CustomerNotFoundException("Customer Not Found ById: "+ id));
    }

    public CustomerDto getCustomerById(String customerId) {

        return  converter.convertToCustomerDto(findCustomerById(customerId));
    }
}
