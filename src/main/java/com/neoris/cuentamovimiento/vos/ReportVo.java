package com.neoris.cuentamovimiento.vos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportVo {
    private AccountResponseVo account;
    private List<TransactionResponseVo> transactions;
}
