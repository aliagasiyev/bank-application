package az.edu.turing.bankingapplication.service.impl;

import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.domain.entity.TransferEntity;
import az.edu.turing.bankingapplication.domain.repository.AccountRepository;
import az.edu.turing.bankingapplication.domain.repository.TransferRepository;
import az.edu.turing.bankingapplication.enums.Bank;
import az.edu.turing.bankingapplication.enums.Currency;
import az.edu.turing.bankingapplication.exception.InsufficientBalanceException;
import az.edu.turing.bankingapplication.mapper.config.TransferMapper;
import az.edu.turing.bankingapplication.model.dto.request.TransferRequest;
import az.edu.turing.bankingapplication.model.dto.response.TransferResponse;
import az.edu.turing.bankingapplication.service.CurrencyRateFetcher;
import az.edu.turing.bankingapplication.service.TransferService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final TransferMapper transferMapper;
    private final CurrencyRateFetcher currencyRate;


    @Override
    @Transactional
    public TransferResponse processTransfer(TransferRequest transferRequest) {

        AccountEntity sender = accountRepository.findById(transferRequest.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));
        AccountEntity recipient = accountRepository.findById(transferRequest.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Recipient account not found"));

        BigDecimal amount = transferRequest.getAmount();
        Currency senderCurrency = sender.getCurrency();
        Currency recipientCurrency = recipient.getCurrency();
        Bank senderBank = sender.getBank();
        Bank recipientBank = recipient.getBank();

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("You have insufficient funds for this transfer.");
        }

        sender.setBalance(sender.getBalance().subtract(amount));

        BigDecimal sendCommission = senderBank.getSendCommission();
        BigDecimal receiveCommission = recipientBank.getReceiptCommission();
        BigDecimal finalAmount = amount.multiply(sendCommission).multiply(receiveCommission);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(currencyRate.fetchRates());

            JsonNode senderRateNode = jsonNode.get(senderCurrency.toString());
            JsonNode recipientRateNode = jsonNode.get(recipientCurrency.toString());

            if (senderRateNode == null || recipientRateNode == null) {
                throw new RuntimeException("Currency rate not found for sender or recipient.");
            }

            BigDecimal senderRate = new BigDecimal(senderRateNode.asText());
            BigDecimal recipientRate = new BigDecimal(recipientRateNode.asText());

            BigDecimal convertedAmount = senderRate
                    .divide(recipientRate, 10, RoundingMode.HALF_UP)
                    .multiply(finalAmount);

            recipient.setBalance(recipient.getBalance().add(convertedAmount));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing currency rates.", e);
        }

        accountRepository.save(sender);
        accountRepository.save(recipient);

        TransferEntity transferEntity = transferMapper.toTransferEntity(transferRequest, sender, recipient);
        TransferEntity savedTransferEntity = transferRepository.save(transferEntity);

        return transferMapper.toTransferResponse(savedTransferEntity);
    }
}