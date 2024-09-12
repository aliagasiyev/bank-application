package az.edu.turing.bankingapplication.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequest (
    @NotBlank
    String fullName,

    @NotBlank
    String pin
)
{
}
