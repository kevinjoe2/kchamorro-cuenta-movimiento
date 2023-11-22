package com.kchamorro.cuentamovimiento.services;

import com.kchamorro.cuentamovimiento.vos.TransactionRequestVo;
import com.kchamorro.cuentamovimiento.vos.TransactionResponseVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Flux<TransactionResponseVo> findAll();
    Mono<TransactionResponseVo> save(TransactionRequestVo transactionRequestDto);
    Mono<TransactionResponseVo> update(Long id, TransactionRequestVo transactionRequestDto);
    Mono<TransactionResponseVo> patch(Long id, TransactionRequestVo transactionRequestDto);
    Mono<Void> delete(Long id);
}
