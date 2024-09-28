package az.edu.turing.bankingapplication.service.impl;

import org.slf4j.Logger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import az.edu.turing.bankingapplication.enums.Currency;
import az.edu.turing.bankingapplication.service.TransferService;
import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.domain.entity.TransferEntity;
import az.edu.turing.bankingapplication.mapper.config.TransferMapper;
import az.edu.turing.bankingapplication.service.CurrencyRateFetcher;
import az.edu.turing.bankingapplication.model.dto.request.TransferRequest;
import az.edu.turing.bankingapplication.domain.repository.AccountRepository;
import az.edu.turing.bankingapplication.model.dto.response.TransferResponse;
import az.edu.turing.bankingapplication.domain.repository.TransferRepository;
import az.edu.turing.bankingapplication.exception.InsufficientBalanceException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final TransferMapper transferMapper;
    private final CurrencyRateFetcher currencyRate;
    private static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

    @Override
    @Transactional
    public TransferResponse processTransfer(TransferRequest transferRequest) {
        log.info("Processing transfer from sender ID {} to recipient ID {} for amount {}",
                transferRequest.getSenderId(), transferRequest.getRecipientId(), transferRequest.getAmount());

        AccountEntity sender = fetchAccount(transferRequest.getSenderId(), "Sender account not found");
        AccountEntity recipient = fetchAccount(transferRequest.getRecipientId(), "Recipient account not found");

        validateSufficientBalance(sender, transferRequest.getAmount());

        BigDecimal finalConvertedAmount = handleCurrencyConversionAndFees(sender, recipient, transferRequest.getAmount());

        updateAccountBalances(sender, recipient, transferRequest.getAmount(), finalConvertedAmount);

        TransferEntity transferEntity = saveTransferDetails(transferRequest, sender, recipient);
        return transferMapper.toTransferResponse(transferEntity);
    }

    private AccountEntity fetchAccount(Long accountId, String errorMessage) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException(errorMessage));
    }

    private void validateSufficientBalance(AccountEntity sender, BigDecimal transferAmount) {
        if (sender.getBalance().compareTo(transferAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient funds in sender's account.");
        }
    }

    private BigDecimal handleCurrencyConversionAndFees(AccountEntity sender, AccountEntity recipient, BigDecimal amount) {
        BigDecimal sendCommission = sender.getBank().getSendCommission();
        BigDecimal receiveCommission = recipient.getBank().getReceiptCommission();
        BigDecimal finalAmountAfterSendFee = amount.subtract(amount.multiply(sendCommission));
        BigDecimal finalCommissionAfterReceiveFee = finalAmountAfterSendFee.subtract(finalAmountAfterSendFee.multiply(receiveCommission));


        return convertCurrency(sender.getCurrency(), recipient.getCurrency(), finalCommissionAfterReceiveFee);
    }

    private BigDecimal convertCurrency(Currency senderCurrency, Currency recipientCurrency, BigDecimal amount) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(currencyRate.fetchRates());

            BigDecimal senderRate = getCurrencyRate(jsonNode, senderCurrency);
            BigDecimal recipientRate = getCurrencyRate(jsonNode, recipientCurrency);

            return senderRate.divide(recipientRate, 10, RoundingMode.HALF_UP).multiply(amount);
        } catch (Exception e) {
            log.error("Error during currency conversion", e);
            throw new RuntimeException("Error processing currency rates.", e);
        }
    }

    private BigDecimal getCurrencyRate(JsonNode jsonNode, Currency currency) {
        JsonNode rateNode = jsonNode.get(currency.toString());
        if (rateNode == null) {
            throw new RuntimeException("Currency rate not found for: " + currency);
        }
        return new BigDecimal(rateNode.asText().replace(",", "."));
    }

    private void updateAccountBalances(AccountEntity sender, AccountEntity recipient, BigDecimal sentAmount, BigDecimal receivedAmount) {
        sender.setBalance(sender.getBalance().subtract(sentAmount));
        recipient.setBalance(recipient.getBalance().add(receivedAmount));
        accountRepository.save(sender);
        accountRepository.save(recipient);
    }

    private TransferEntity saveTransferDetails(TransferRequest transferRequest, AccountEntity sender, AccountEntity recipient) {
        TransferEntity transferEntity = transferMapper.toTransferEntity(transferRequest, sender, recipient);
        return transferRepository.save(transferEntity);
    }
}
