package az.edu.turing.bankingapplication.mapper.config;

import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.domain.entity.TransferEntity;
import az.edu.turing.bankingapplication.model.dto.request.TransferRequest;
import az.edu.turing.bankingapplication.model.dto.response.TransferResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "recipient", source = "recipient")
    TransferEntity toTransferEntity(TransferRequest transferRequest, AccountEntity sender, AccountEntity recipient);

    TransferResponse toTransferResponse(TransferEntity transferEntity);
}
