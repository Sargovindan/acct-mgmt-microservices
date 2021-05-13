package com.account.management.customer.rest;

import com.account.management.customer.dto.CustomerDto;
import com.account.management.customer.rest.responses.AppResponse;
import com.account.management.customer.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping({ "api/customers" })
@Slf4j
public class CustomerController extends BaseController{

    @Autowired
    private CustomerService customerService;

    @GetMapping("/check")
    public AppResponse check()
            throws IllegalArgumentException {

        return getResponse(new CustomerDto());
    }

    @PostMapping
    public AppResponse<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto)
            throws IllegalArgumentException, MethodArgumentNotValidException {
        log.info("create customer with customer name==== {}", customerDto.getFirstname());

        return getResponse(customerService.createCustomer(customerDto));
    }

    @GetMapping("/{customerId}")
    public AppResponse<CustomerDto> getCustomerById(@PathVariable(name = "customerId") String customerId)
            throws IllegalArgumentException {
        log.info("get customer details with customer id==== {}", customerId);

        return getResponse(customerService.getByCustomerId(customerId));
    }

}
