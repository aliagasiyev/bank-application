package az.edu.turing.bankingapplication.model.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(
        Long id,
        String pin,
        String fullName,
        List<Long> accounts
) {
}
