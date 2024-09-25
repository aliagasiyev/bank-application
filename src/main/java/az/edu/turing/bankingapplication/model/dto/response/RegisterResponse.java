package az.edu.turing.bankingapplication.model.dto.response;

import lombok.Builder;

import java.util.Set;

@Builder
public record RegisterResponse(
        Long id,
        String username,
        String email
) {}
