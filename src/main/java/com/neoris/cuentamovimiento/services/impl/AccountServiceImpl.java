package com.neoris.cuentamovimiento.services.impl;

import com.neoris.cuentamovimiento.entities.AccountEntity;
import com.neoris.cuentamovimiento.entities.TransactionEntity;
import com.neoris.cuentamovimiento.repos.AccountRepo;
import com.neoris.cuentamovimiento.services.AccountService;
import com.neoris.cuentamovimiento.utils.mappers.AccountMapper;
import com.neoris.cuentamovimiento.utils.mappers.PatchGeneralMapper;
import com.neoris.cuentamovimiento.vos.AccountRequestVo;
import com.neoris.cuentamovimiento.vos.AccountResponseVo;
import com.neoris.cuentamovimiento.vos.CustomerResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

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
    public Mono<AccountResponseVo> findById(Long accountId) {
        return accountRepo.findById(accountId)
                .switchIfEmpty(Mono.error(new RuntimeException("La cuenta con el numero " + accountId + " no existe")))
                .flatMap(this::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseVo> save(Mono<AccountRequestVo> accountRequestDto) {
        return accountRequestDto
                .flatMap(this::saveAccount)
                .flatMap(this::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseVo> update(Long id, Mono<AccountRequestVo> accountRequestDto) {
        return accountRequestDto.flatMap(requestDto -> accountRepo.findById(id)
                        .flatMap(accountEntity -> {
                            accountMapper.putAccountEntityFromVo(requestDto, accountEntity);
                            return accountRepo.save(accountEntity);
                        }))
                .flatMap(this::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseVo> patch(Long id, Mono<AccountRequestVo> accountRequestDto) {
        return accountRequestDto.flatMap(requestDto -> accountRepo.findById(id)
                        .flatMap(accountEntity -> {
                            patchGeneralMapper.patchAccountEntityFromVo(requestDto, accountEntity);
                            return accountRepo.save(accountEntity);
                        }))
                .flatMap(this::toAccountResponseDto);
    }

    @Override
    public Mono<AccountResponseVo> patchAccountByTransaction(TransactionEntity transactionEntity){
        return accountRepo.findById(transactionEntity.getAccountId())
                .switchIfEmpty(Mono.error(new RuntimeException("La cuenta con el id " + transactionEntity.getAccountId() + " no existe")))
                .flatMap(entity -> {
                    entity.setBalance(entity.getBalance().add(transactionEntity.getTransactionValue()));
                    return accountRepo.save(entity)
                            .onErrorMap(errorMap -> new RuntimeException("Error al actualizar el balance de la cuenta"));
                })
                .flatMap(this::toAccountResponseDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.error(new RuntimeException("No implementado"));
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
                            .onErrorMap(errorMap -> new RuntimeException("Error al guardar la cuenta"));
                });
    }

    private Mono<AccountResponseVo> toAccountResponseDto(AccountEntity accountEntity){
        return Mono.just(accountEntity)
                .map(accountMapper::toAccountResponseDto);
    }

    private Mono<CustomerResponseVo> getCustomer(Long customerId){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("customers")
                        .queryParam("id", "{id}")
                        .build(customerId))
                .retrieve()
                .bodyToFlux(CustomerResponseVo.class)
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .next();
    }
}