package az.edu.turing.bankingapplication.mapper.config;

import az.edu.turing.bankingapplication.domain.entity.UserEntity;
import az.edu.turing.bankingapplication.model.dto.request.UserRequest;
import az.edu.turing.bankingapplication.model.dto.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toUserEntity(UserRequest userRequest);
    UserResponse toUserDto(UserEntity userEntity);

    List<UserResponse> listToUserDto(List<UserEntity> userEntity);
}
