package az.edu.turing.bankingapplication.domain.repository;

import az.edu.turing.bankingapplication.domain.entity.CurrencyRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyRateEntity, Long> {
}
