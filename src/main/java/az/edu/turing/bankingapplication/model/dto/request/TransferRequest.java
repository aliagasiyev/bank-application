package az.edu.turing.bankingapplication.model.dto.request;

import az.edu.turing.bankingapplication.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private Currency currency;
    private String description;
}
