package az.edu.turing.bankingapplication.model.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(

        String username,
        String password,
        String email
) {
}
