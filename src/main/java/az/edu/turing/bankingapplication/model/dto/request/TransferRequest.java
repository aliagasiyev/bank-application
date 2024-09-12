package az.edu.turing.bankingapplication.model.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private Long senderId;
    private Long recipientId;
    private BigDecimal amount;
    private String currency;
    private String description;
}
