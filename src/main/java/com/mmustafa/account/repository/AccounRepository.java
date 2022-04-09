package com.mmustafa.account.repository;

import com.mmustafa.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccounRepository extends JpaRepository<Account , String> {
}
