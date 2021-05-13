package com.account.management.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {

    private String customerId;

    @NotNull(message = "Please provide a firstname")
    @NotBlank
    @NotEmpty
    private String firstname;

    @NotNull(message = "Please provide a surname")
    private String surname;
}
