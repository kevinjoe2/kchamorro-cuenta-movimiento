package com.kchamorro.cuentamovimiento.services.impl;

import com.kchamorro.cuentamovimiento.entities.TransactionEntity;
import com.kchamorro.cuentamovimiento.exceptions.CuentaMovimientoException;
import com.kchamorro.cuentamovimiento.utils.mappers.AccountMapper;
import com.kchamorro.cuentamovimiento.utils.mappers.PatchGeneralMapper;
import com.kchamorro.cuentamovimiento.entities.AccountEntity;
import com.kchamorro.cuentamovimiento.repos.AccountRepo;
import com.kchamorro.cuentamovimiento.services.AccountService;
import com.kchamorro.cuentamovimiento.vos.AccountRequestVo;
import com.kchamorro.cuentamovimiento.vos.AccountResponseVo;
import com.kchamorro.cuentamovimiento.vos.CustomerResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;
    private final PatchGeneralMapper patchGeneralMapper;
    private final WebClient webClient;

    @Override
    public Flux<AccountResponseVo> findAll() {
        return accountRepo.findAll()
                .flatMap(this::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseVo> findById(UUID accountId) {
        return accountRepo.findById(accountId)
                .switchIfEmpty(Mono.error(new CuentaMovimientoException("La cuenta con el numero " + accountId + " no existe")))
                .flatMap(this::toAccountResponseDto);
    }

    @Override
    public Mono<AccountEntity> findEntityById(UUID accountId) {
        return accountRepo.findById(accountId)
                .switchIfEmpty(Mono.error(new CuentaMovimientoException("La cuenta con el numero " + accountId + " no existe")));
    }

    @Override
    public Mono<AccountResponseVo> save(AccountRequestVo accountRequestDto) {
        return saveAccount(accountRequestDto)
                .flatMap(this::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseVo> update(UUID id, AccountRequestVo accountRequestDto) {
        return accountRepo.findById(id)
                .flatMap(accountEntity -> {
                    accountMapper.putAccountEntityFromVo(accountRequestDto, accountEntity);
                    return accountRepo.save(accountEntity);
                })
                .flatMap(this::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseVo> updateBalance(UUID id, BigDecimal balance) {
        return accountRepo.findById(id)
                .flatMap(accountEntity -> {
                    accountEntity.setBalance(balance);
                    return accountRepo.save(accountEntity);
                })
                .flatMap(this::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseVo> patch(UUID id, AccountRequestVo accountRequestDto) {
        return accountRepo.findById(id)
                .flatMap(accountEntity -> {
                    patchGeneralMapper.patchAccountEntityFromVo(accountRequestDto, accountEntity);
                    return accountRepo.save(accountEntity);
                })
                .flatMap(this::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseVo> patchAccountByTransaction(TransactionEntity transactionEntity){
        return accountRepo.findById(transactionEntity.getAccountId())
                .switchIfEmpty(Mono.error(new CuentaMovimientoException("La cuenta con el id " + transactionEntity.getAccountId() + " no existe")))
                .flatMap(entity -> {
                    entity.setBalance(entity.getBalance().add(transactionEntity.getTransactionValue()));
                    return accountRepo.save(entity)
                            .onErrorMap(errorMap -> new CuentaMovimientoException("Error al actualizar el balance de la cuenta"));
                })
                .flatMap(this::toAccountResponseDto);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return Mono.error(new CuentaMovimientoException("No implementado"));
    }

    private Mono<AccountEntity> saveAccount(AccountRequestVo requestDto) {
        return getCustomer(requestDto.getCustomerId())
                .flatMap(customer -> {
                    AccountEntity account = AccountEntity.builder()
                            .accountType(requestDto.getAccountType())
                            .balance(BigDecimal.valueOf(0))
                            .state("ACT")
                            .customerId(customer.getId())
                            .build();
                    return accountRepo.save(account)
                            .onErrorMap(errorMap -> new CuentaMovimientoException("Error al guardar la cuenta"));
                });
    }

    private Mono<AccountResponseVo> toAccountResponseDto(AccountEntity accountEntity){
        return Mono.just(accountEntity)
                .map(accountMapper::toAccountResponseVo);
    }

    private Mono<CustomerResponseVo> getCustomer(UUID customerId){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("customers")
                        .queryParam("id", "{id}")
                        .build(customerId))
                .retrieve()
                .bodyToFlux(CustomerResponseVo.class)
                .switchIfEmpty(Mono.error(new CuentaMovimientoException("Cliente no existe")))
                .next();
    }
}