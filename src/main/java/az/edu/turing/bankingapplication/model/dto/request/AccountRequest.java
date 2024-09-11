package az.edu.turing.bankingapplication.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AccountRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank @Email String email,
        @NotBlank byte[] profilePhoto // It's good to ensure this isn't null
) {
}
