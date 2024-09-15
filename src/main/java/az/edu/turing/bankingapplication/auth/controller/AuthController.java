package az.edu.turing.bankingapplication.auth.controller;

import az.edu.turing.bankingapplication.auth.model.response.AuthResponse;
import az.edu.turing.bankingapplication.auth.service.AuthService;
import az.edu.turing.bankingapplication.model.dto.request.LoginRequest;
import az.edu.turing.bankingapplication.model.dto.request.RegisterRequest;
import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;
import az.edu.turing.bankingapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;

    @PostMapping("/{userId}/register")
    public ResponseEntity<RegisterResponse> createAccount(@PathVariable Long userId, @RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = accountService.createAccount(userId, registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser (@RequestBody LoginRequest loginRequest){
        return authService.loginUser(loginRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logoutUser (){
        return authService.logoutUser();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken (@RequestBody String refreshToken){
        return authService.refreshToken(refreshToken);
    }
}
