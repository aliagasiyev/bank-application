package az.edu.turing.bankingapplication.domain.entity;

import az.edu.turing.bankingapplication.enums.Currency;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transfer")
public class TransferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "from_account", nullable = false)
    private String fromAccount;

    @Column (name = "to_account", nullable = false)
    private String toAccount;

    @Column (nullable = false)
    private BigDecimal amount;

    @Column (nullable = false)
    private Currency currency;

    @Column
    private String description;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();
}
