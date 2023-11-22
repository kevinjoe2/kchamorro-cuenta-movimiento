package com.neoris.cuentamovimiento.repos;

import com.neoris.cuentamovimiento.entities.TransactionEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TransactionRepo extends ReactiveCrudRepository<TransactionEntity, UUID> {

    Flux<TransactionEntity> findByAccountId(UUID accountId);
    Flux<TransactionEntity> findByAccountIdAndTransactionDateAfter(UUID accountId, LocalDateTime after);

}
