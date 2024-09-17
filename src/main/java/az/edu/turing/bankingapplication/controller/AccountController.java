package az.edu.turing.bankingapplication.controller;

import az.edu.turing.bankingapplication.auth.model.request.RegisterRequest;
import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;
import az.edu.turing.bankingapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/users/{userId}/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<RegisterResponse> createAccount(@PathVariable Long userId, @RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = accountService.createAccount(userId, registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<RegisterResponse> getAccount(@PathVariable Long userId, @PathVariable Long accountId) {
        Optional<RegisterResponse> registerResponse = accountService.getAccount(accountId);
        return registerResponse.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<RegisterResponse> updateAccount(@PathVariable Long accountId, @RequestBody RegisterRequest registerRequest) {
        Optional<RegisterResponse> updatedAccount = accountService.updateAccount(accountId, registerRequest);
        return updatedAccount.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok("Account deactivated successfully.");
    }

    @PutMapping("/{accountId}/activate")
    public ResponseEntity<String> activateAccount(@PathVariable Long accountId) {
        accountService.activateAccount(accountId);
        return ResponseEntity.ok("Account activated successfully.");
    }
}
