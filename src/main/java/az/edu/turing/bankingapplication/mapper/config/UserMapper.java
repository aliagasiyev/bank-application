package az.edu.turing.bankingapplication.mapper.config;

import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.domain.entity.UserEntity;
import az.edu.turing.bankingapplication.model.dto.request.UserRequest;
import az.edu.turing.bankingapplication.model.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "accounts", ignore = true) // Ignore account mapping or handle separately
    UserEntity toUserEntity(UserRequest userRequest);

    @Mapping(target = "accounts", source = "accounts", qualifiedByName = "mapAccountIds")
    UserResponse toUserDto(UserEntity userEntity);

    @Named("mapAccountIds")
    default List<Long> map(List<AccountEntity> accounts) {
        return accounts.stream().map(AccountEntity::getId).collect(Collectors.toList());
    }
}
