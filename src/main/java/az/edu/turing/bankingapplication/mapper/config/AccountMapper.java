package az.edu.turing.bankingapplication.mapper.config;

import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.model.dto.request.AccountRequest;
import az.edu.turing.bankingapplication.model.dto.response.AccountResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountEntity toAccountEntity(AccountRequest accountRequest);
    AccountResponse toAccountDto( AccountEntity accountEntity);

    List<AccountEntity> listToAccountEntity(List<AccountRequest> accountRequest);
    List<AccountResponse> listToAccountDto(List<AccountEntity> accountEntity);
}
