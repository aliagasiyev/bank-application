package az.edu.turing.bankingapplication.domain.entity;

import az.edu.turing.bankingapplication.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "budget")
public class BudgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "azn_balance")
    private BigDecimal AZN = BigDecimal.ZERO;

    @Column(name = "usd_balance")
    private BigDecimal USD = BigDecimal.ZERO;

    @Column(name = "eur_balance")
    private BigDecimal EUR = BigDecimal.ZERO;

    public BigDecimal getBudget(Currency currency) {
        switch (currency) {
            case AZN:
                return AZN;
            case USD:
                return USD;
            case EUR:
                return EUR;
            default:
                throw new IllegalArgumentException("Currency not supported");
        }
    }

    private void setBudget(Currency currency, BigDecimal amount) {
        switch (currency) {
            case AZN:
                AZN = amount;
                break;
            case USD:
                USD = amount;
                break;
            case EUR:
                EUR = amount;
                break;
            default:
                throw new IllegalArgumentException("Currency not supported");
        }
    }

    public void subtractAmount(Currency currency, BigDecimal amount) {
        BigDecimal currentAmount = getBudget(currency);
        if (currentAmount.compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        setBudget(currency, currentAmount.subtract(amount));
    }

    public void addAmount(Currency currency, BigDecimal amount) {
        BigDecimal currentAmount = getBudget(currency);
        setBudget(currency, currentAmount.add(amount));
    }
}