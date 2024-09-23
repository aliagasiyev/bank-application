package az.edu.turing.bankingapplication.service.impl;

import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.domain.entity.UserEntity;
import az.edu.turing.bankingapplication.domain.repository.AccountRepository;
import az.edu.turing.bankingapplication.domain.repository.UserRepository;
import az.edu.turing.bankingapplication.enums.AccountStatus;
import az.edu.turing.bankingapplication.exception.UserNotFoundException;
import az.edu.turing.bankingapplication.mapper.config.AccountMapper;
import az.edu.turing.bankingapplication.auth.model.request.RegisterRequest;
import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;
import az.edu.turing.bankingapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.security.auth.login.AccountNotFoundException;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse createAccount(Long userId, RegisterRequest registerRequest) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        byte[] profilePhotoBytes = Base64.getDecoder().decode(registerRequest.profilePhoto());


        AccountEntity accountEntity = accountMapper.toAccountEntity(registerRequest);
        accountEntity.setUser(userEntity);
        accountEntity.setPassword(passwordEncoder.encode(registerRequest.password()));
        accountEntity.setStatus(AccountStatus.ACTIVATED);
        accountEntity.setProfilePhoto(profilePhotoBytes);

        AccountEntity savedAccount = accountRepository.save(accountEntity);
        return accountMapper.toAccountDto(savedAccount);
    }

    @Override
    public Optional<RegisterResponse> getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .map(accountMapper::toAccountDto)
                .or(() -> {
                    try {
                        throw new AccountNotFoundException("Account not found with id: " + accountId);
                    } catch (AccountNotFoundException e) {
                        throw new RuntimeException(e);}});
    }

    @Override
    public Optional<RegisterResponse> updateAccount(Long accountId, RegisterRequest registerRequest) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountEntity.setUsername(registerRequest.username());
        accountEntity.setPassword(passwordEncoder.encode(registerRequest.password()));
        accountEntity.setEmail(registerRequest.email());

        byte[] profilePhotoBytes = Base64.getDecoder().decode(registerRequest.profilePhoto());
        accountEntity.setProfilePhoto(profilePhotoBytes);

        AccountEntity updatedAccount = accountRepository.save(accountEntity);
        return Optional.of(accountMapper.toAccountDto(updatedAccount));
    }

    @Override
    public void deleteAccount(Long accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
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
