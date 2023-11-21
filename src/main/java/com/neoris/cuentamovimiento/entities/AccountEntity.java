package com.neoris.cuentamovimiento.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    private Long id;

    @Column("account_type")
    private String accountType;

    @Column("balance")
    private BigDecimal balance;

    private String state;

    @Column("customer_id")
    private Long customerId;
}
