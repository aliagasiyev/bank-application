package az.edu.turing.bankingapplication.service;

import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.domain.entity.UserEntity;
import az.edu.turing.bankingapplication.domain.repository.AccountRepository;
import az.edu.turing.bankingapplication.domain.repository.UserRepository;
import az.edu.turing.bankingapplication.enums.AccountStatus;
import az.edu.turing.bankingapplication.exception.UserNotFoundException;
import az.edu.turing.bankingapplication.mapper.config.AccountMapper;
import az.edu.turing.bankingapplication.model.dto.request.AccountRequest;
import az.edu.turing.bankingapplication.model.dto.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    public AccountResponse createProfile(Long u_id, AccountRequest accountRequest) {
        UserEntity userEntity = userRepository.findById(u_id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + u_id));

        AccountEntity accountEntity = accountMapper.toAccountEntity(accountRequest);
        accountEntity.setUser(userEntity);
        AccountEntity savedProfile = accountRepository.save(accountEntity);

        return accountMapper.toAccountDto(savedProfile);
    }

    public Optional<AccountResponse> getProfile(Long u_id, Long a_id) {
        UserEntity userEntity = userRepository.findById(u_id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + u_id));

        Optional<AccountEntity> accountEntity = accountRepository.findById(a_id)
                .filter(account -> account.getUser().getId().equals(u_id));

        if (accountEntity.isPresent()) {
            return Optional.of(accountMapper.toAccountDto(accountEntity.get()));
        } else {
            return Optional.empty();
        }
    }

    public Optional<AccountResponse> updateProfile(Long u_id, Long a_id, AccountRequest accountDto) {
        return Optional.empty();
    }

    public void deleteAccount(Long a_id) {
        AccountEntity accountEntity = accountRepository.findById(a_id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountEntity.setStatus(AccountStatus.DEACTIVATED);
        accountRepository.save(accountEntity);
    }

    public void activateAccount(Long accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountEntity.setStatus(AccountStatus.ACTIVATED);
        accountRepository.save(accountEntity);
    }
}
