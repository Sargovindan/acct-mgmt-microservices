package com.account.management.customer.rest;

import com.account.management.customer.dto.CustomerDto;
import com.account.management.customer.repositories.CustomerRepository;
import com.account.management.customer.services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CustomerIntegrationTest extends RestApiTest{

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldCheckNameIsRequired() throws Exception {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstname(null);
        String request = asJsonString(customerDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/customers").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.status", is("error")));


    }

    @Test
    void shouldCreateCustomer() throws Exception {

        CustomerDto customerDto = createDto();
        int count = customerRepository.findAll().size();
        String request = asJsonString(customerDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/customers").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.data.customerId", notNullValue()));

        int newCount = customerRepository.findAll().size();
        Assertions.assertEquals(count+1, newCount);

    }

    private CustomerDto createDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstname("John");
        customerDto.setSurname("Smith");

        return customerDto;
    }
}
