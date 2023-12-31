package com.kchamorro.cuentamovimiento.vos;

import com.kchamorro.cuentamovimiento.utils.enums.AccountTypeEnum;
import lombok.*;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestVo {
    @NonNull
    private AccountTypeEnum accountType;
    private BigDecimal balance;
    @NonNull
    private UUID customerId;
}
