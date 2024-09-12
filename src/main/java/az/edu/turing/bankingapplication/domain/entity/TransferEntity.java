package az.edu.turing.bankingapplication.domain.entity;

import az.edu.turing.bankingapplication.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transfer")
public class TransferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private Currency currency;
    private String description;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    private AccountEntity sender;

    @ManyToOne
    @JoinColumn(name = "recipient_account_id")
    private AccountEntity recipient;
}
