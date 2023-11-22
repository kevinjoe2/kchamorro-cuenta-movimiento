package com.neoris.cuentamovimiento.entities;

import com.neoris.cuentamovimiento.utils.enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    private UUID id;

    @Column("transaction_date")
    private LocalDateTime transactionDate;

    @Column("transaction_type")

    private TransactionTypeEnum transactionType;

    @Column("transaction_value")
    private BigDecimal transactionValue;

    @Column("account_initial_balance")
    private BigDecimal accountInitialBalance;

    @Column("account_ending_balance")
    private BigDecimal accountEndingBalance;

    @Column("account_id")
    private UUID accountId;

}
