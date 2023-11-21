package com.neoris.cuentamovimiento.vos;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class TransactionRequestVo {
    private final String accountNumber;
    private final BigDecimal transactionValue;
}
