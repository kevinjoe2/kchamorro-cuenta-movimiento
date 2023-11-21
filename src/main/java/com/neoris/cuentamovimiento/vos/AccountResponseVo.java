package com.neoris.cuentamovimiento.vos;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class AccountResponseVo {
    private String accountType;
    private BigDecimal balance;
    private String state;
    private Long customerId;
}
