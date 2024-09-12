package az.edu.turing.bankingapplication.domain.entity;

import az.edu.turing.bankingapplication.enums.AccountStatus;
import az.edu.turing.bankingapplication.enums.Bank;
import az.edu.turing.bankingapplication.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column (nullable = false)
    @Enumerated (EnumType.STRING)
    private Bank bank;

    @Column (nullable = false)
    @Enumerated (EnumType.STRING)
    private Currency currency;

    @Column
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus status;

    @Lob
    @Column(name = "profile_photo", nullable = false)
    private byte[] profilePhoto;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
}
