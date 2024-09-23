package az.edu.turing.bankingapplication.auth.controller;

import az.edu.turing.bankingapplication.auth.model.response.AuthResponse;
import az.edu.turing.bankingapplication.auth.service.AuthService;
import az.edu.turing.bankingapplication.model.dto.request.LoginRequest;
import az.edu.turing.bankingapplication.auth.model.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/{userId}/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest registerRequest){
        return authService.registerUser(registerRequest);
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
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        return authService.refreshToken(refreshToken);
    }
}
