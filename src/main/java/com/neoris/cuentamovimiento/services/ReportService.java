package com.neoris.cuentamovimiento.services;

import com.neoris.cuentamovimiento.vos.TransactionResponseVo;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReportService {

    Mono<Map<UUID, List<TransactionResponseVo>>> get(
            LocalDate dateFrom, LocalDate dateTo
    );

}
