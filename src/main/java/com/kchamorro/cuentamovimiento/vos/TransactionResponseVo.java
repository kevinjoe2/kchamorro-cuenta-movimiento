package com.kchamorro.cuentamovimiento.vos;

import com.kchamorro.cuentamovimiento.utils.enums.TransactionTypeEnum;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class TransactionResponseVo {

    private UUID id;
    private LocalDateTime transactionDate;
    private TransactionTypeEnum transactionType;
    private BigDecimal transactionValue;
    private BigDecimal accountInitialBalance;
    private BigDecimal accountEndingBalance;
    private UUID accountId;
}
