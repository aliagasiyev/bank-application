package az.edu.turing.bankingapplication.service.impl;

import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.domain.entity.TransferEntity;
import az.edu.turing.bankingapplication.domain.repository.AccountRepository;
import az.edu.turing.bankingapplication.domain.repository.TransferRepository;
import az.edu.turing.bankingapplication.enums.Currency;
import az.edu.turing.bankingapplication.exception.InsufficientBalanceException;
import az.edu.turing.bankingapplication.mapper.config.TransferMapper;
import az.edu.turing.bankingapplication.model.dto.request.TransferRequest;
import az.edu.turing.bankingapplication.model.dto.response.TransferResponse;
import az.edu.turing.bankingapplication.service.TransferService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final TransferMapper transferMapper;

    @Override
    @Transactional
    public TransferResponse processTransfer(TransferRequest transferRequest) {

        AccountEntity sender = accountRepository.findById(transferRequest.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));
        AccountEntity recipient = accountRepository.findById(transferRequest.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Recipient account not found"));

        BigDecimal amount = transferRequest.getAmount();
        String currencyString = transferRequest.getCurrency();
        Currency currency = Currency.valueOf(currencyString);

        BigDecimal senderBalance = sender.getBudget().getBudget(currency);
        if (senderBalance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("You have insufficient funds for this transfer.");
        }

        sender.getBudget().subtractAmount(currency, amount);

        recipient.getBudget().addAmount(currency, amount);

        accountRepository.save(sender);
        accountRepository.save(recipient);

        TransferEntity transferEntity = transferMapper.toTransferEntity(transferRequest, sender, recipient);

        TransferEntity savedTransferEntity = transferRepository.save(transferEntity);

        return transferMapper.toTransferResponse(savedTransferEntity);
    }
}
