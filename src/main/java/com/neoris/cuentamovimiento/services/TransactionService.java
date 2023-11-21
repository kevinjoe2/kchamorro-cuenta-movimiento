package com.neoris.cuentamovimiento.services;

import com.neoris.cuentamovimiento.vos.TransactionRequestVo;
import com.neoris.cuentamovimiento.vos.TransactionResponseVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Flux<TransactionResponseVo> findAll();
    Mono<TransactionResponseVo> save(Mono<TransactionRequestVo> transactionRequestDto);
    Mono<TransactionResponseVo> update(Long id, Mono<TransactionRequestVo> transactionRequestDto);
    Mono<TransactionResponseVo> patch(Long id, Mono<TransactionRequestVo> transactionRequestDto);
    Mono<Void> delete(Long id);
}
