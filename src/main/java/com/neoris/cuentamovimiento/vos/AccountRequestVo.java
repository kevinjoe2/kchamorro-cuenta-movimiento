package com.neoris.cuentamovimiento.vos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountRequestVo {
    private String accountType;
    private Long customerId;
}
