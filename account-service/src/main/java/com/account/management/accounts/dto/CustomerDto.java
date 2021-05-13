package com.account.management.accounts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {

    private String customerId;

    @NotNull(message = "Please provide a firstname")
    private String firstname;

    @NotNull(message = "Please provide a surname")
    private String surname;
}
