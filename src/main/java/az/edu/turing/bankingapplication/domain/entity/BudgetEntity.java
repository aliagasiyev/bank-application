package az.edu.turing.bankingapplication.domain.entity;

import az.edu.turing.bankingapplication.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BudgetEntity {
    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Currency, BigDecimal> budgets = new HashMap<>();

    public BigDecimal getBudget(Currency currency) {
        return budgets.getOrDefault(currency, BigDecimal.ZERO);
    }

    public void subtractAmount(Currency currency, BigDecimal amount) {
        BigDecimal currentAmount = getBudget(currency);
        budgets.put(currency, currentAmount.subtract(amount));
    }

    public void addAmount(Currency currency, BigDecimal amount) {
        BigDecimal currentAmount = getBudget(currency);
        budgets.put(currency, currentAmount.add(amount));
    }
}
