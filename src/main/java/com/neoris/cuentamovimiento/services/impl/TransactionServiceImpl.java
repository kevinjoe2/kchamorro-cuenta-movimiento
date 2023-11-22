package com.neoris.cuentamovimiento.services.impl;

import com.neoris.cuentamovimiento.entities.AccountEntity;
import com.neoris.cuentamovimiento.entities.TransactionEntity;
import com.neoris.cuentamovimiento.exceptions.CuentaMovimientoException;
import com.neoris.cuentamovimiento.repos.TransactionRepo;
import com.neoris.cuentamovimiento.services.AccountService;
import com.neoris.cuentamovimiento.services.TransactionService;
import com.neoris.cuentamovimiento.utils.enums.TransactionTypeEnum;
import com.neoris.cuentamovimiento.utils.mappers.TransactionMapper;
import com.neoris.cuentamovimiento.vos.TransactionRequestVo;
import com.neoris.cuentamovimiento.vos.TransactionResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    private static final BigDecimal LIMIT = BigDecimal.valueOf(1000L);

    @Override
    public Flux<TransactionResponseVo> findAll() {
        return transactionRepo.findAll()
                .map(transactionMapper::toTransactionResponseVo);
    }

    @Override
    @Transactional
    public Mono<TransactionResponseVo> save(TransactionRequestVo transactionRequestVo){
        return saveTransaction(transactionRequestVo)
                .map(transactionMapper::toTransactionResponseVo);
    }

    @Override
    public Mono<TransactionResponseVo> update(Long id, TransactionRequestVo transactionRequestVo) {
        return Mono.error(new CuentaMovimientoException("No implementado"));
    }

    @Override
    public Mono<TransactionResponseVo> patch(Long id, TransactionRequestVo transactionRequestVo) {
        return Mono.error(new CuentaMovimientoException("No implementado"));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return null;
    }

    private Mono<TransactionEntity> saveTransaction(TransactionRequestVo transactionRequestVo) {
        return validateAccountAndTransaction(transactionRequestVo)
                .flatMap(account -> {
                    TransactionEntity trans = TransactionEntity.builder()
                            .transactionDate(LocalDateTime.now())
                            .transactionType(BigDecimal.ZERO.compareTo(transactionRequestVo.getTransactionValue()) > 0 ? TransactionTypeEnum.RETIRO : TransactionTypeEnum.DEPOSITO)
                            .transactionValue(transactionRequestVo.getTransactionValue())
                            .accountInitialBalance(account.getBalance())
                            .accountEndingBalance(account.getBalance().add(transactionRequestVo.getTransactionValue()))
                            .accountId(account.getId()).build();
                    return transactionRepo.save(trans)
                            .flatMap(transSave -> accountService.updateBalance(
                                    account.getId(),
                                    account.getBalance().add(transSave.getTransactionValue())))
                            .map(accountSave -> trans);
                });
    }

    private Mono<AccountEntity> validateAccountAndTransaction(TransactionRequestVo transactionRequestVo){
        return accountService.findEntityById(transactionRequestVo.getAccountId())
                .switchIfEmpty(Mono.error(new CuentaMovimientoException("La cuenta no existe")))
                .<AccountEntity>handle((account, sink) -> {
                    if (BigDecimal.ZERO.compareTo(account.getBalance().add(transactionRequestVo.getTransactionValue())) <= 0) {
                        sink.next(account);
                    } else {
                        sink.error(new CuentaMovimientoException("Saldo no disponible"));
                    }
                })
                .flatMap(account -> validateTransaction(transactionRequestVo).map(valid -> account));
    }

    private Mono<Boolean> validateTransaction(TransactionRequestVo transactionRequestVo){
        LocalDateTime after = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        if (LIMIT.negate().compareTo(transactionRequestVo.getTransactionValue()) > 0) {
            throw new CuentaMovimientoException("El maximo numero de retiro es $" + LIMIT + " diarios");
        }
        if (BigDecimal.ZERO.compareTo(transactionRequestVo.getTransactionValue()) == 0) {
            throw new CuentaMovimientoException("Debe ingresar una cantidad distinta de cero");
        }
        return transactionRepo.findByAccountIdAndTransactionDateAfter(transactionRequestVo.getAccountId(), after)
                .filter(filter -> BigDecimal.ZERO.compareTo(filter.getTransactionValue()) >= 0)
                .defaultIfEmpty(TransactionEntity.builder().transactionValue(BigDecimal.ZERO).build())
                .map(TransactionEntity::getTransactionValue)
                .reduce(BigDecimal::add)
                .map(value -> value.add(transactionRequestVo.getTransactionValue()))
                .handle((value, sink) -> {
                    if (LIMIT.negate().compareTo(value) <= 0) {
                        sink.next(true);
                    } else {
                        sink.error(new CuentaMovimientoException("El maximo numero de retiro es $" + LIMIT + " diarios"));
                    }
                });
    }
}