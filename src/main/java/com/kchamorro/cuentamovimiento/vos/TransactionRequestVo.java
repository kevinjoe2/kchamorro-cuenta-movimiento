package com.kchamorro.cuentamovimiento.vos;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
public class TransactionRequestVo {
    @NonNull
    private final UUID accountId;
    @NonNull
    private final BigDecimal transactionValue;
}
