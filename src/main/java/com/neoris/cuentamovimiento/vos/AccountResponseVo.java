package com.neoris.cuentamovimiento.vos;

import com.neoris.cuentamovimiento.utils.enums.AccountTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseVo {
    private UUID id;
    private AccountTypeEnum accountType;
    private BigDecimal balance;
    private String state;
    private UUID customerId;
}
