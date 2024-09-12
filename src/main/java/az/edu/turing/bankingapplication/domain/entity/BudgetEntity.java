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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "budget")
public class BudgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "amount")
    private Map<Currency, BigDecimal> budgets = new HashMap<>();

    public BigDecimal getBudget(Currency currency) {
        return budgets.getOrDefault(currency, BigDecimal.ZERO);
    }

    public void setBudget(Currency currency, BigDecimal amount) {
        budgets.put(currency, amount);
    }
}
