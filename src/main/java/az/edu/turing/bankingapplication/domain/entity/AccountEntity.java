package az.edu.turing.bankingapplication.domain.entity;

import az.edu.turing.bankingapplication.auth.model.enums.Role;
import az.edu.turing.bankingapplication.enums.AccountStatus;
import az.edu.turing.bankingapplication.enums.Bank;
import az.edu.turing.bankingapplication.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "profile_photo", nullable = false)
    private byte[] profilePhoto;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "is_account_non_expired")
    private boolean accountNonExpired = true;

    @Column(name = "is_account_non_locked")
    private boolean accountNonLocked = true;

    @Column(name = "is_credentials_non_expired")
    private boolean credentialsNonExpired = true;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
}
