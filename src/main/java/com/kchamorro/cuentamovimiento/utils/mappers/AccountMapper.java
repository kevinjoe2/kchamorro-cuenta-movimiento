package com.kchamorro.cuentamovimiento.utils.mappers;

import com.kchamorro.cuentamovimiento.entities.AccountEntity;
import com.kchamorro.cuentamovimiento.vos.AccountRequestVo;
import com.kchamorro.cuentamovimiento.vos.AccountResponseVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    AccountResponseVo toAccountResponseVo(AccountEntity entity);
    void putAccountEntityFromVo(AccountRequestVo requestDto, @MappingTarget AccountEntity entity);
}
