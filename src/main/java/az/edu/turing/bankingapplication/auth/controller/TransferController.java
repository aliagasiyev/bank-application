package az.edu.turing.bankingapplication.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import az.edu.turing.bankingapplication.service.TransferService;
import az.edu.turing.bankingapplication.model.dto.request.TransferRequest;
import az.edu.turing.bankingapplication.model.dto.response.TransferResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transfer")

public class TransferController {
    
    private final TransferService transferService;
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <TransferResponse> transferMoney (@RequestBody TransferRequest transferRequest){
        TransferResponse transferResponse = transferService.processTransfer(transferRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transferResponse);
    }
}
