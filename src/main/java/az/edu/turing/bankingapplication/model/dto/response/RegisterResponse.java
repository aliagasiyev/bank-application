package az.edu.turing.bankingapplication.model.dto.response;

import az.edu.turing.bankingapplication.domain.entity.UserEntity;
import az.edu.turing.bankingapplication.enums.AccountStatus;
import lombok.Builder;

@Builder
public record RegisterResponse(
        Long id,
        String username,
        String password,
        String email,
        AccountStatus status,
        UserEntity user
) {
}
