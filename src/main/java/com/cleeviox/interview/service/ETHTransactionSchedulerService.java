package com.cleeviox.interview.service;

import static com.cleeviox.interview.dto.TransactionStatusDTO.PENDING;

import com.cleeviox.interview.model.TransactionStatus;
import com.cleeviox.interview.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

@Service
public class ETHTransactionSchedulerService {

    private static final String TOKEN_ID = "ETH";

    private static final Logger logger = LoggerFactory.getLogger(ETHTransactionSchedulerService.class);

    private final Web3j web3j;

    private final ETHTransactionService ethTransactionService;

    private final TransactionRepository transactionRepository;

    /**
     * @param web3j
     * @param ethTransactionService
     * @param transactionRepository
     */
    public ETHTransactionSchedulerService(Web3j web3j, ETHTransactionService ethTransactionService, TransactionRepository transactionRepository) {
        this.web3j = web3j;
        this.ethTransactionService = ethTransactionService;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Check the status of all pending transactions
     */
    @Scheduled(fixedRate = 10000)
    public void checkTransactionStatus() {
        transactionRepository.findAllByStatus(TransactionStatus.builder().code(PENDING.toString()).build())
                             .forEach(transaction -> ethTransactionService.checkTransactionStatus(transaction.getTxHead()));
    }

}
