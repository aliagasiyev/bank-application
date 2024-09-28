package az.edu.turing.bankingapplication.service;

import az.edu.turing.bankingapplication.model.dto.request.TransferRequest;
import az.edu.turing.bankingapplication.model.dto.response.TransferResponse;

public interface TransferService {

    TransferResponse processTransfer (TransferRequest transferRequest);
}
