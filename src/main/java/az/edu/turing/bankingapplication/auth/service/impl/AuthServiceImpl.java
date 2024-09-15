package az.edu.turing.bankingapplication.auth.service.impl;


import az.edu.turing.bankingapplication.auth.model.enums.Role;
import az.edu.turing.bankingapplication.auth.model.response.AuthResponse;
import az.edu.turing.bankingapplication.auth.security.JwtTokenProvider;
import az.edu.turing.bankingapplication.auth.service.AuthService;
import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.domain.repository.AccountRepository;
import az.edu.turing.bankingapplication.mapper.config.AccountMapper;
import az.edu.turing.bankingapplication.model.dto.request.LoginRequest;
import az.edu.turing.bankingapplication.model.dto.request.RegisterRequest;
import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoderUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    @Override
    public ResponseEntity<AuthResponse> registerUser(RegisterRequest registerRequest) {
        if (accountRepository.existsByUsername(registerRequest.username())) {
            return ResponseEntity.badRequest().body(new AuthResponse("Username already exists"));
        }
        if (accountRepository.existsByEmail(registerRequest.email())) {
            return ResponseEntity.badRequest().body(new AuthResponse("Email already exists"));
        }

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(registerRequest.username());
        accountEntity.setEmail(registerRequest.email());
        accountEntity.setPassword(passwordEncoderUtil.encode(registerRequest.password()));
        accountEntity.setRoles(Set.of(Role.USER));

        accountRepository.save(accountEntity);

        String token = jwtTokenProvider.createAccessToken(accountEntity.getUsername(), accountEntity.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(accountEntity.getUsername(), accountEntity.getRoles());

        RegisterResponse registerResponse = accountMapper.toAccountDto(accountEntity);

        return ResponseEntity.ok(new AuthResponse("Account registered successfully", token, refreshToken, accountEntity.getRoles(), registerResponse));
    }

    @Override
    public ResponseEntity<AuthResponse> loginUser(LoginRequest loginRequest) {
        AccountEntity accountEntity = accountRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (!passwordEncoderUtil.matches(loginRequest.password(), accountEntity.getPassword())) {
            return ResponseEntity.badRequest().body(new AuthResponse("Invalid credentials"));
        }
        String token = jwtTokenProvider.createAccessToken(accountEntity.getUsername(), accountEntity.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(accountEntity.getUsername(), accountEntity.getRoles());

        RegisterResponse registerResponse = accountMapper.toAccountDto(accountEntity);
        return ResponseEntity.ok(new AuthResponse("Account registered successfully", token, refreshToken, accountEntity.getRoles(), registerResponse));

    }

    @Override
    public ResponseEntity<AuthResponse> logoutUser() {
        AuthResponse authResponse = new AuthResponse("Logged out successfully");
        return ResponseEntity.ok(authResponse);
    }

    @Override
    public ResponseEntity<AuthResponse> refreshToken(String refreshToken) {
        String newToken = jwtTokenProvider.refreshToken(refreshToken);

        AuthResponse authResponse = new AuthResponse("Token refreshed", newToken);
        return ResponseEntity.ok(authResponse);
    }
}

