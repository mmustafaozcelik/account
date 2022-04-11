package com.mmustafa.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mmustafa.account.dto.AccountDtoConverter;
import com.mmustafa.account.dto.CreateAccountRequest;
import com.mmustafa.account.model.Customer;
import com.mmustafa.account.repository.AccounRepository;
import com.mmustafa.account.repository.CustomerRepository;
import com.mmustafa.account.service.AccountService;
import com.mmustafa.account.service.CustomerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import java.math.BigDecimal;
import java.time.Clock;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
       "server-port=0",
        "command.line.runner.enabled=false"
})
@RunWith(SpringRunner.class)
@DirtiesContext
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Clock clock;

    @MockBean
    private Supplier<UUID> uuidSupplier;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccounRepository accounRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountDtoConverter converter;

    private AccountService service = new AccountService(accounRepository,customerService,converter);
    private ObjectMapper mapper =new ObjectMapper();

    @BeforeEach
    public  void setup(){
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
    }

    @Test
    public void testCreateAccount_WhenCustomerIdExits_shouldCreateAccountAnbReturnAccountDto() throws Exception{
        Customer customer = customerRepository.save(new Customer("Mustafa" , "ÖZ"));
        CreateAccountRequest request = new CreateAccountRequest(customer.getId(), new BigDecimal(100));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance", is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.id", is(customer.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.name", is(customer.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.surname", is(customer.getSurname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions", Matchers.hasSize(1)));


    }

}