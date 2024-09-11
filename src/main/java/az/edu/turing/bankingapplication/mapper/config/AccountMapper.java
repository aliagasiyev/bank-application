package az.edu.turing.bankingapplication.mapper.config;

import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.model.dto.request.AccountRequest;
import az.edu.turing.bankingapplication.model.dto.response.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring") 
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "profilePhoto", target = "profilePhoto")
    @Mapping(target = "user", ignore = true)
    AccountEntity toAccountEntity(AccountRequest accountRequest);

    AccountResponse toAccountDto(AccountEntity accountEntity);

    List<AccountEntity> listToAccountEntity(List<AccountRequest> accountRequest);

    List<AccountResponse> listToAccountDto(List<AccountEntity> accountEntity);
}
