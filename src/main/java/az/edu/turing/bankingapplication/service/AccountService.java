package az.edu.turing.bankingapplication.service;

import az.edu.turing.bankingapplication.auth.model.request.RegisterRequest;
import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;

import java.util.Optional;

public interface AccountService {
    RegisterResponse createAccount(Long u_id, RegisterRequest registerRequest);

    Optional<RegisterResponse> getAccount(Long a_id);

    Optional<RegisterResponse> updateAccount(Long accountId, RegisterRequest registerRequest);

    void deleteAccount(Long a_id);

    void activateAccount(Long accountId);
}
