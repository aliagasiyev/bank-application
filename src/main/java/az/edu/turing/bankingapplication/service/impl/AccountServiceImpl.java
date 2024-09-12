package az.edu.turing.bankingapplication.service.impl;

import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.domain.entity.UserEntity;
import az.edu.turing.bankingapplication.domain.repository.AccountRepository;
import az.edu.turing.bankingapplication.domain.repository.UserRepository;
import az.edu.turing.bankingapplication.enums.AccountStatus;
import az.edu.turing.bankingapplication.exception.UserNotFoundException;
import az.edu.turing.bankingapplication.mapper.config.AccountMapper;
import az.edu.turing.bankingapplication.model.dto.request.RegisterRequest;
import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;
import az.edu.turing.bankingapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    @Override
    public RegisterResponse createAccount(Long u_id, RegisterRequest registerRequest) {
        UserEntity userEntity = userRepository.findById(u_id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + u_id));

        AccountEntity accountEntity = accountMapper.toAccountEntity(registerRequest);
        accountEntity.setUser(userEntity);
        AccountEntity savedProfile = accountRepository.save(accountEntity);

        return accountMapper.toAccountDto(savedProfile);
    }

    @Override
    public Optional<RegisterResponse> getAccount(Long a_id) {
//        UserEntity userEntity = userRepository.findById(u_id)
//                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + u_id));

        Optional<AccountEntity> accountEntity = accountRepository.findById(a_id);
//               .filter(account -> account.getUser().getId().equals(u_id));

        if (accountEntity.isPresent()) {
            return Optional.of(accountMapper.toAccountDto(accountEntity.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RegisterResponse> updateAccount(Long accountId, RegisterRequest registerRequest) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                //   .filter(account -> account.getUser().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Account not found for the given user."));

        accountEntity.setUsername(registerRequest.username());
        accountEntity.setPassword(registerRequest.password());
        accountEntity.setEmail(registerRequest.email());
        accountEntity.setProfilePhoto(registerRequest.profilePhoto());

        AccountEntity updatedAccount = accountRepository.save(accountEntity);
        return Optional.of(accountMapper.toAccountDto(updatedAccount));
    }

    @Override
    public void deleteAccount(Long a_id) {
        AccountEntity accountEntity = accountRepository.findById(a_id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountEntity.setStatus(AccountStatus.DEACTIVATED);
        accountRepository.save(accountEntity);
    }

    @Override
    public void activateAccount(Long accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountEntity.setStatus(AccountStatus.ACTIVATED);
        accountRepository.save(accountEntity);
    }
}
