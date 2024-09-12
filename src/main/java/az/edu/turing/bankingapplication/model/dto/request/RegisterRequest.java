package az.edu.turing.bankingapplication.model.dto.request;

import az.edu.turing.bankingapplication.enums.Currency;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank @Email String email,
        @NotBlank byte[] profilePhoto,
        @NotBlank Currency currency
        ) {
}
