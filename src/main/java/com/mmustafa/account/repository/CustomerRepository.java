package com.mmustafa.account.repository;

import com.mmustafa.account.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer , String> {
}
