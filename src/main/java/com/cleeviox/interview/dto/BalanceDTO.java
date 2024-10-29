package com.cleeviox.interview.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Builder
@Getter
@Setter
public class BalanceDTO {

    private String tokenId;

    private BigDecimal balance;

}
