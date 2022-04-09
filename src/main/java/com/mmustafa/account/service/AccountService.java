package com.mmustafa.account.service;

import com.mmustafa.account.dto.AccountDto;
import com.mmustafa.account.dto.AccountDtoConverter;
import com.mmustafa.account.dto.CreateAccountRequest;
import com.mmustafa.account.model.Account;
import com.mmustafa.account.model.Customer;
import com.mmustafa.account.model.Transaction;
import com.mmustafa.account.repository.AccounRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountService {

    private final AccounRepository accounRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter converter;

    public AccountService(AccounRepository accounRepository,
                          CustomerService customerService,

                          AccountDtoConverter converter) {
        this.accounRepository = accounRepository;
        this.customerService = customerService;
        this.converter = converter;
    }


    public AccountDto createAccount(CreateAccountRequest createAccountRequest){
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account(
                customer,
                createAccountRequest.getInitialCredit(),
                LocalDateTime.now());
        if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0){
            Transaction transaction =  new Transaction(createAccountRequest.getInitialCredit(),account);
                    account.getTransaction().add(transaction);
        }
        return converter.convert(accounRepository.save(account));
    }

}
