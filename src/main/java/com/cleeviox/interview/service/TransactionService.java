package com.cleeviox.interview.service;

import com.cleeviox.interview.dto.BalanceDTO;
import com.cleeviox.interview.dto.TransactionDTO;

import java.math.BigDecimal;

public interface TransactionService {

    /**
     * Retrieves the balance for the given address
     *
     * @param address Address to retrieve the balance for
     * @return BalanceDTO containing the balance
     */
    public BalanceDTO getBalance(String address);

    /**
     * Executes a transaction from the given address to the given address with the given amount
     *
     * @param fromAddress Address to send the transaction from
     * @param secret      Secret key for the fromAddress
     * @param toAddress   Address to send the transaction to
     * @param amount      Amount to send
     * @return TransactionDTO containing the transaction details
     */
    public TransactionDTO executeTransaction(String fromAddress, String secret, String toAddress, BigDecimal amount);

}
