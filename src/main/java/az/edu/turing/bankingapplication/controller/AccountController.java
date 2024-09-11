package az.edu.turing.bankingapplication.controller;

import az.edu.turing.bankingapplication.model.dto.request.AccountRequest;
import az.edu.turing.bankingapplication.model.dto.response.AccountResponse;
import az.edu.turing.bankingapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/users/{userId}/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@PathVariable Long userId, @RequestBody AccountRequest accountRequest) {
        AccountResponse accountResponse = accountService.createAccount(userId, accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long userId, @PathVariable Long accountId) {
        Optional<AccountResponse> accountResponse = accountService.getAccount(userId, accountId);
        return accountResponse.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Long userId, @PathVariable Long accountId, @RequestBody AccountRequest accountRequest) {
        Optional<AccountResponse> updatedAccount = accountService.updateAccount(userId, accountId, accountRequest);
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
