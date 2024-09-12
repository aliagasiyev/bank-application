package az.edu.turing.bankingapplication.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {
    private Long id;
    private BigDecimal amount;
    private String senderName;
    private String recipientName;
    private LocalDateTime createdAt;
}