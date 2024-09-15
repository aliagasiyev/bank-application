package az.edu.turing.bankingapplication.auth.service;

import az.edu.turing.bankingapplication.auth.model.response.AuthResponse;
import az.edu.turing.bankingapplication.model.dto.request.LoginRequest;
import az.edu.turing.bankingapplication.model.dto.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<AuthResponse> registerUser(RegisterRequest registerRequest);

    ResponseEntity<AuthResponse> loginUser(LoginRequest loginRequest);

    ResponseEntity<AuthResponse> logoutUser();

    ResponseEntity<AuthResponse> refreshToken(String refreshToken);
}
