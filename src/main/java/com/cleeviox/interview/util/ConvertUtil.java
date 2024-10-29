package com.cleeviox.interview.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ConvertUtil {

    private static final BigDecimal WEI_TO_ETH_CONVERSION = BigDecimal.valueOf(1_000_000_000_000_000_000L);

    /**
     * Converts balance from Wei to Ether.
     *
     * @param weiBalance balance in Wei
     * @return balance in Ether
     */
    public static BigDecimal convertWeiToEth(BigInteger weiBalance) {
        return new BigDecimal(weiBalance).divide(WEI_TO_ETH_CONVERSION);
    }

}
