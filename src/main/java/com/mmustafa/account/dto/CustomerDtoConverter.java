package com.mmustafa.account.dto;

import com.mmustafa.account.model.Customer;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {
    private final  CustomerAccountDtoConverter converter;

    public CustomerDtoConverter(CustomerAccountDtoConverter converter) {
        this.converter = converter;
    }


    public AccountCustomerDto convertToAccountCustomer(Customer from){
        if (from == null){
            return new AccountCustomerDto("","" ,"" );
        }
        return new AccountCustomerDto(from.getId(), from.getName(), from.getSurName());
    }
    public CustomerDto convertToCustomerDto(Customer from){
        return  new CustomerDto(
                from.getId(),
                from.getName(),
                from.getSurName(),
                from.getAccounts().stream().map(converter::convert).collect(Collectors.toSet())
        );
    }
}
