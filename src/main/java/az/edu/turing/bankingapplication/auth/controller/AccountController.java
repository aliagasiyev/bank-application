package az.edu.turing.bankingapplication.auth.controller;

import az.edu.turing.bankingapplication.auth.model.request.RegisterRequest;
import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;
import az.edu.turing.bankingapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<RegisterResponse> getAccount(@PathVariable Long userId, @PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccount(accountId).get());
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<RegisterResponse> updateAccount(@PathVariable Long accountId, @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(accountService.updateAccount(accountId, registerRequest).get());
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
