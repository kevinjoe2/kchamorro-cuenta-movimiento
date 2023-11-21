package com.neoris.cuentamovimiento.services;

import com.neoris.cuentamovimiento.entities.TransactionEntity;
import com.neoris.cuentamovimiento.vos.AccountRequestVo;
import com.neoris.cuentamovimiento.vos.AccountResponseVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<AccountResponseVo> findAll();
    Mono<AccountResponseVo> findById(Long accountId);
    Mono<AccountResponseVo> save(Mono<AccountRequestVo> accountRequestDto);
    Mono<AccountResponseVo> update(Long id, Mono<AccountRequestVo> accountRequestDto);
    Mono<AccountResponseVo> patch(Long id, Mono<AccountRequestVo> accountRequestDto);
    Mono<AccountResponseVo> patchAccountByTransaction(TransactionEntity transactionEntity);
    Mono<Void> delete(Long id);
}
