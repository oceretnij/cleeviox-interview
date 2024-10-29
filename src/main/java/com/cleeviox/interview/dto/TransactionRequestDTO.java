package com.cleeviox.interview.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class TransactionRequestDTO {

    private String fromAddress;

    private String secret;

    private String toAddress;

    private BigDecimal amount;

}
