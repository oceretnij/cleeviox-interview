package com.cleeviox.interview.controller;

import com.cleeviox.interview.dto.BalanceDTO;
import com.cleeviox.interview.dto.TransactionDTO;
import com.cleeviox.interview.dto.TransactionRequestDTO;
import com.cleeviox.interview.service.ETHTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eth")
public final class ETHTransactionController implements BaseTransactionController {

    private final ETHTransactionService ethTransactionService;

    /**
     * @param ethTransactionService
     */
    public ETHTransactionController(ETHTransactionService ethTransactionService) {
        this.ethTransactionService = ethTransactionService;
    }

    /**
     * Retrieve the balance of the account
     */
    @Override
    @GetMapping("/balance/{address}")
    public ResponseEntity<BalanceDTO> getBalance(@PathVariable String address) {
        return ResponseEntity.ok(ethTransactionService.getBalance(address));
    }

    /**
     * Execute a transaction
     */
    @Override
    @PostMapping("/transaction")
    public ResponseEntity<TransactionDTO> executeTransaction(@RequestBody TransactionRequestDTO requestDTO) {
        return ResponseEntity.ok(ethTransactionService.executeTransaction(
                requestDTO.getFromAddress(),
                requestDTO.getSecret(),
                requestDTO.getToAddress(),
                requestDTO.getAmount()));
    }

}
