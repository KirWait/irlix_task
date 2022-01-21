package com.irlix.task.mapper;

import com.irlix.task.dto.TransactionResponseDto;
import com.irlix.task.entity.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {
    TransactionResponseDto transactionEntityToTransactionResponseDto(TransactionEntity entity);
}
