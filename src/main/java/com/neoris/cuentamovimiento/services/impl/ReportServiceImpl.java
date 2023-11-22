package com.neoris.cuentamovimiento.services.impl;

import com.neoris.cuentamovimiento.services.AccountService;
import com.neoris.cuentamovimiento.services.ReportService;
import com.neoris.cuentamovimiento.services.TransactionService;
import com.neoris.cuentamovimiento.vos.AccountResponseVo;
import com.neoris.cuentamovimiento.vos.ReportVo;
import com.neoris.cuentamovimiento.vos.TransactionResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final TransactionService transactionService;

    @Override
    public Mono<Map<UUID, List<TransactionResponseVo>>> get(LocalDate dateFrom, LocalDate dateTo) {
        return transactionService.findAll()

                // FILTER BY DATE FROM
                .filter(transactionResponseDto -> dateFrom != null ?
                        transactionResponseDto.getTransactionDate().toLocalDate().isAfter(dateFrom)
                                || transactionResponseDto.getTransactionDate().toLocalDate().equals(dateFrom) : Boolean.TRUE)

                // FILTER BY DATE TO
                .filter(transactionResponseDto -> dateTo != null ?
                        transactionResponseDto.getTransactionDate().toLocalDate().isBefore(dateTo)
                                || transactionResponseDto.getTransactionDate().toLocalDate().equals(dateTo) : Boolean.TRUE)

                .collect(Collectors.groupingBy(TransactionResponseVo::getAccountId));
    }
}
