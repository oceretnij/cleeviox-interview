package com.cleeviox.interview.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @Column(name = "tx_head")
    private String txHead;

    @Column(name = "from_address")
    private String fromAddress;

    @Column(name = "to_address")
    private String toAddress;

    private BigDecimal amount;

    @Column(name = "token_id")
    private String tokenId;

    @ManyToOne(targetEntity = TransactionStatus.class)
    @JoinColumn(name = "status", referencedColumnName = "code")
    private TransactionStatus status;

}
