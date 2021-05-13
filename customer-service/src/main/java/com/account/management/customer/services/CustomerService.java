package com.account.management.customer.services;

import com.account.management.customer.dto.CustomerDto;
import com.account.management.customer.exceptions.RecordNotFoundException;
import com.account.management.customer.models.Customer;
import com.account.management.customer.repositories.CustomerRepository;
import com.account.management.customer.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDto getByCustomerId(String customerId) {

        Customer c = customerRepository.findByCustomerId(customerId).orElseThrow(
                () -> new RecordNotFoundException("Customer not found - " + customerId)
        );

        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(c.getCustomerId());
        customerDto.setSurname(c.getSurname());
        customerDto.setFirstname(c.getName());

        return customerDto;
    }

    public CustomerDto createCustomer(CustomerDto customerDto)
            throws IllegalArgumentException {
        log.info("Creating a customer==== {} ", customerDto.getFirstname());

        Customer customer = createEntity(customerDto);
        customerRepository.save(customer);
        log.info("saved with id==== {}", customer.getId());
        customerDto.setCustomerId(customer.getCustomerId());
        return customerDto;

    }

    private Customer createEntity(CustomerDto customerDto) throws NoSuchElementException {

        Customer customer = new Customer();
        customer.setCustomerId(AppUtils.generateCustomerNumber());
        customer.setName(customerDto.getFirstname());
        customer.setSurname(customerDto.getSurname());

        return customer;
    }
}
