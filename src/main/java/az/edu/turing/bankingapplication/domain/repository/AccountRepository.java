package az.edu.turing.bankingapplication.domain.repository;

import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
