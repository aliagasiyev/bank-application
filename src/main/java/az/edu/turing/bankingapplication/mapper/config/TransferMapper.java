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
    @Mapping(target = "senderCurrency", source = "sender.currency")
    @Mapping(target = "recipientCurrency", source = "recipient.currency")
    @Mapping(target = "amount", source = "transferRequest.amount")
    @Mapping(target = "description", source = "transferRequest.description")
    TransferEntity toTransferEntity(TransferRequest transferRequest, AccountEntity sender, AccountEntity recipient);

    // Add explicit mappings for senderName and recipientName
    @Mapping(target = "senderName", source = "sender.username")
    @Mapping(target = "recipientName", source = "recipient.username")
    TransferResponse toTransferResponse(TransferEntity transferEntity);
}
