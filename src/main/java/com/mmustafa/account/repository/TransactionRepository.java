package com.mmustafa.account.repository;

import com.mmustafa.account.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction , String> {
}
