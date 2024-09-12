package az.edu.turing.bankingapplication.mapper.config;

import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.model.dto.request.RegisterRequest;
import az.edu.turing.bankingapplication.model.dto.response.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(target = "bank", ignore = true)
    @Mapping(source = "currency",target = "currency")
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "profilePhoto", target = "profilePhoto")
    @Mapping(target = "user", ignore = true)
    AccountEntity toAccountEntity(RegisterRequest registerRequest);

    RegisterResponse toAccountDto(AccountEntity accountEntity);

    List<AccountEntity> listToAccountEntity(List<RegisterRequest> registerRequest);

    List<RegisterResponse> listToAccountDto(List<AccountEntity> accountEntity);
}
