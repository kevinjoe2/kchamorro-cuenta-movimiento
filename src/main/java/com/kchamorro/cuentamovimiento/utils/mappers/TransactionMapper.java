package com.kchamorro.cuentamovimiento.utils.mappers;

import com.kchamorro.cuentamovimiento.entities.TransactionEntity;
import com.kchamorro.cuentamovimiento.vos.TransactionResponseVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    TransactionResponseVo toTransactionResponseVo(TransactionEntity entity);
}
