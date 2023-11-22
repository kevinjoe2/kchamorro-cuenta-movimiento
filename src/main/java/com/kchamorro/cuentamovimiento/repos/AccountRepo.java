package com.kchamorro.cuentamovimiento.repos;

import com.kchamorro.cuentamovimiento.entities.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepo extends ReactiveCrudRepository<AccountEntity, UUID> {

}
