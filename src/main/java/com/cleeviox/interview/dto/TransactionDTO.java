package com.cleeviox.interview.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class TransactionDTO {

    private String transactionHash;

    private String fromAddress;

    private String toAddress;

    private String tokenId;

    private BigDecimal amount;

    private TransactionStatusDTO status;

}
