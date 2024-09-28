package az.edu.turing.bankingapplication.auth.service.impl;


import az.edu.turing.bankingapplication.auth.model.response.AuthResponse;
import az.edu.turing.bankingapplication.auth.security.JwtTokenProvider;
import az.edu.turing.bankingapplication.auth.service.AuthService;
import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.domain.repository.AccountRepository;
import az.edu.turing.bankingapplication.domain.repository.UserRepository;
import az.edu.turing.bankingapplication.enums.AccountStatus;
import az.edu.turing.bankingapplication.mapper.config.AccountMapper;
import az.edu.turing.bankingapplication.model.dto.request.LoginRequest;
import az.edu.turing.bankingapplication.auth.model.request.RegisterRequest;
import az.edu.turing.bankingapplication.model.dto.response.LoginResponse;
import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoderUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    @Override
    public ResponseEntity<RegisterResponse> registerAccount(RegisterRequest registerRequest) {
        if (accountRepository.existsByUsername(registerRequest.username())) {
            return ResponseEntity.badRequest().body(new RegisterResponse("Username already exists"));
        }
        if (accountRepository.existsByEmail(registerRequest.email())) {
            return ResponseEntity.badRequest().body(new RegisterResponse("Email already exists"));
        }
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(registerRequest.username());
        accountEntity.setEmail(registerRequest.email());
        accountEntity.setPassword(passwordEncoderUtil.encode(registerRequest.password()));
        accountEntity.setBank(registerRequest.bank());
        accountEntity.setCurrency(registerRequest.currency());
        accountEntity.setProfilePhoto(registerRequest.profilePhoto());

        accountEntity.setStatus(AccountStatus.ACTIVATED);

        accountRepository.save(accountEntity);

        RegisterResponse registerResponse = accountMapper.toAccountDto(accountEntity);

        return ResponseEntity.ok(new RegisterResponse("Account registered successfully", registerResponse));
    }

    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest) {
        AccountEntity accountEntity = accountRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!passwordEncoderUtil.matches(loginRequest.password(), accountEntity.getPassword())) {
            return ResponseEntity.badRequest().body(new LoginResponse("Invalid credentials"));
        }

        String token = jwtTokenProvider.createAccessToken(accountEntity.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(accountEntity.getUsername());

        LoginResponse loginResponse = LoginResponse.builder()
                .message("Login successful")
                .username(accountEntity.getUsername())
                .email(accountEntity.getEmail())
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();

        return ResponseEntity.ok(loginResponse);
    }



    @Override
    public ResponseEntity<AuthResponse> logoutUser() {
        AuthResponse authResponse = new AuthResponse("Logged out successfully");
        return ResponseEntity.ok(authResponse);
    }

    @Override
    public ResponseEntity<AuthResponse> refreshToken(String refreshToken) {
        AuthResponse authResponse = jwtTokenProvider.refreshToken(refreshToken);
        return ResponseEntity.ok(authResponse);
    }


}

