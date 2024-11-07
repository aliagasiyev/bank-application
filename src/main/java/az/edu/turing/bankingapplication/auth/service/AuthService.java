package az.edu.turing.bankingapplication.auth.service;

import az.edu.turing.bankingapplication.auth.model.response.AuthResponse;
import az.edu.turing.bankingapplication.model.dto.request.LoginRequest;
import az.edu.turing.bankingapplication.auth.model.request.RegisterRequest;
import az.edu.turing.bankingapplication.model.dto.response.LoginResponse;
import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<RegisterResponse> registerAccount(Long userId, RegisterRequest registerRequest);

    ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest);

    ResponseEntity<AuthResponse> logoutUser();

    ResponseEntity<AuthResponse> refreshToken(String refreshToken);
}
