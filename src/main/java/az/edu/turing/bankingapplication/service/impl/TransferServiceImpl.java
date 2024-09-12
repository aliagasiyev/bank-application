package az.edu.turing.bankingapplication.service.impl;

import az.edu.turing.bankingapplication.domain.entity.TransferEntity;
import az.edu.turing.bankingapplication.domain.repository.TransferRepository;
import az.edu.turing.bankingapplication.model.dto.request.TransferRequest;
import az.edu.turing.bankingapplication.model.dto.response.TransferResponse;
import az.edu.turing.bankingapplication.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;

    @Override
    public TransferResponse processTransfer (TransferRequest transferRequest) {
        TransferEntity transferEntity = new TransferEntity();
        transferEntity.setFromAccount(transferRequest.getFromAccount());
        transferEntity.setToAccount(transferRequest.getToAccount());
        transferEntity.setAmount(transferRequest.getAmount());
        transferEntity.setCurrency(transferRequest.getCurrency());
        transferEntity.setCreatedAt(LocalDateTime.now());
        transferEntity.setDescription(transferRequest.getDescription());
        return transferRepository.save(transferEntity);
    }
}
