package com.neoris.cuentamovimiento.repos;

import com.neoris.cuentamovimiento.entities.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface AccountRepo extends ReactiveCrudRepository<AccountEntity, UUID> {

}
