package az.edu.turing.bankingapplication.service.impl;

import az.edu.turing.bankingapplication.domain.entity.TransferEntity;
import az.edu.turing.bankingapplication.domain.repository.TransferRepository;
import az.edu.turing.bankingapplication.mapper.config.TransferMapper;
import az.edu.turing.bankingapplication.model.dto.request.TransferRequest;
import az.edu.turing.bankingapplication.model.dto.response.TransferResponse;
import az.edu.turing.bankingapplication.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;

    @Override
    public TransferResponse processTransfer(TransferRequest transferRequest) {

        TransferEntity transferEntity = transferMapper.toTransferEntity(transferRequest);

        TransferEntity savedTransferEntity = transferRepository.save(transferEntity);

        return transferMapper.toTransferResponse(savedTransferEntity);
    }
}
