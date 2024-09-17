package az.edu.turing.bankingapplication.auth.model.request;

import az.edu.turing.bankingapplication.enums.Bank;
import az.edu.turing.bankingapplication.enums.Currency;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank @Email String email,
        @NotNull byte[] profilePhoto, // Changed from @NotBlank to @NotNull
        @NotNull Currency currency,
        @NotNull Bank bank
) {}
