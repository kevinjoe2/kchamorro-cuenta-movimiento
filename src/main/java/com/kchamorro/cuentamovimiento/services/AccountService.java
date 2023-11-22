package com.kchamorro.cuentamovimiento.services;

import com.kchamorro.cuentamovimiento.entities.AccountEntity;
import com.kchamorro.cuentamovimiento.entities.TransactionEntity;
import com.kchamorro.cuentamovimiento.vos.AccountRequestVo;
import com.kchamorro.cuentamovimiento.vos.AccountResponseVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
    Flux<AccountResponseVo> findAll();
    Mono<AccountResponseVo> findById(UUID accountId);
    Mono<AccountEntity> findEntityById(UUID accountId);
    Mono<AccountResponseVo> save(AccountRequestVo accountRequestDto);
    Mono<AccountResponseVo> update(UUID id, AccountRequestVo accountRequestDto);
    Mono<AccountResponseVo> updateBalance(UUID id, BigDecimal balance);
    Mono<AccountResponseVo> patch(UUID id, AccountRequestVo accountRequestDto);
    Mono<AccountResponseVo> patchAccountByTransaction(TransactionEntity transactionEntity);
    Mono<Void> delete(UUID id);
}
