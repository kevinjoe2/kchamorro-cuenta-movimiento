package com.neoris.cuentamovimiento.utils.mappers;

import com.neoris.cuentamovimiento.entities.AccountEntity;
import com.neoris.cuentamovimiento.vos.AccountRequestVo;
import com.neoris.cuentamovimiento.vos.AccountResponseVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    AccountResponseVo toAccountResponseVo(AccountEntity entity);
    void putAccountEntityFromVo(AccountRequestVo requestDto, @MappingTarget AccountEntity entity);
}
