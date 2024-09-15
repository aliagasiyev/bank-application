package az.edu.turing.bankingapplication.model.dto.response;

import az.edu.turing.bankingapplication.auth.model.enums.Role;
import lombok.Builder;

import java.util.Set;

@Builder
public record RegisterResponse(
        Long id,
        String username,
        String email,
        Set<Role> roles
) {}
