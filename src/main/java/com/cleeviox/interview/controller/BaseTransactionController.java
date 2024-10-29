package com.cleeviox.interview.controller;

import com.cleeviox.interview.dto.BalanceDTO;
import com.cleeviox.interview.dto.TransactionDTO;
import com.cleeviox.interview.dto.TransactionRequestDTO;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface BaseTransactionController {

    /**
     * Retrieve the balance of the accountw
     */
    public ResponseEntity<BalanceDTO> getBalance(@PathVariable String address);

    /**
     * Execute a transaction
     */
    public ResponseEntity<TransactionDTO> executeTransaction(final @RequestBody TransactionRequestDTO requestDTO);

}
