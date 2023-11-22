package com.neoris.cuentamovimiento.services;

import com.neoris.cuentamovimiento.entities.AccountEntity;
import com.neoris.cuentamovimiento.entities.TransactionEntity;
import com.neoris.cuentamovimiento.vos.AccountRequestVo;
import com.neoris.cuentamovimiento.vos.AccountResponseVo;
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
