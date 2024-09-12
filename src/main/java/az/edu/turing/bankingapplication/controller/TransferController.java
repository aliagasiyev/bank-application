package az.edu.turing.bankingapplication.controller;

import az.edu.turing.bankingapplication.model.dto.request.TransferRequest;
import az.edu.turing.bankingapplication.model.dto.response.TransferResponse;
import az.edu.turing.bankingapplication.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transfer")
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity <TransferResponse> transferMoney (@RequestBody TransferRequest transferRequest){
        TransferResponse transferResponse = transferService.processTransfer(transferRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transferResponse);
    }
}
