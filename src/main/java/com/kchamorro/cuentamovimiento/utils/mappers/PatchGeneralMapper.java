package com.kchamorro.cuentamovimiento.utils.mappers;

import com.kchamorro.cuentamovimiento.entities.AccountEntity;
import com.kchamorro.cuentamovimiento.entities.TransactionEntity;
import com.kchamorro.cuentamovimiento.vos.AccountRequestVo;
import com.kchamorro.cuentamovimiento.vos.TransactionRequestVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface PatchGeneralMapper {
    void patchAccountEntityFromVo(AccountRequestVo requestDto, @MappingTarget AccountEntity entity);
    void patchTransactionEntityFromVo(TransactionRequestVo requestDto, @MappingTarget TransactionEntity entity);
}
