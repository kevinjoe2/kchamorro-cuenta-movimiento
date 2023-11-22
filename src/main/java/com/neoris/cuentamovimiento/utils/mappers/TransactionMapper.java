package com.neoris.cuentamovimiento.utils.mappers;

import com.neoris.cuentamovimiento.entities.TransactionEntity;
import com.neoris.cuentamovimiento.vos.TransactionResponseVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    TransactionResponseVo toTransactionResponseVo(TransactionEntity entity);
}
