package com.cleeviox.interview.service;

import static com.cleeviox.interview.dto.TransactionStatusDTO.FAILED;
import static com.cleeviox.interview.dto.TransactionStatusDTO.PENDING;
import static com.cleeviox.interview.dto.TransactionStatusDTO.SUCCESS;
import static com.cleeviox.interview.util.ConvertUtil.convertWeiToEth;
import static org.web3j.protocol.core.DefaultBlockParameterName.LATEST;
import static org.web3j.utils.Convert.Unit.ETHER;
import static org.web3j.utils.Convert.Unit.GWEI;
import static org.web3j.utils.Convert.toWei;

import com.cleeviox.interview.dto.BalanceDTO;
import com.cleeviox.interview.dto.TransactionDTO;
import com.cleeviox.interview.mapper.TransactionMapper;
import com.cleeviox.interview.model.Transaction;
import com.cleeviox.interview.model.TransactionStatus;
import com.cleeviox.interview.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class ETHTransactionService implements TransactionService {

    private static final String TOKEN_ID = "ETH";

    private static final Logger logger = LoggerFactory.getLogger(ETHTransactionService.class);

    private final Web3j web3j;

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    /**
     * @param web3j
     * @param transactionRepository
     * @param transactionMapper
     */
    public ETHTransactionService(Web3j web3j, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.web3j = web3j;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    /**
     * Retrieves the balance for the given Ethereum address
     *
     * @param address Ethereum address to retrieve the balance for
     * @return BalanceDTO containing the balance in ETH
     */
    public BalanceDTO getBalance(String address) {
        try {
            // Retrieves balance for the given address in Wei and converts it to ETH
            BigInteger weiBalance = web3j.ethGetBalance(address, LATEST)
                                         .sendAsync()
                                         .thenApply(result -> result.getBalance())
                                         .exceptionally(ex -> {
                                             throw new RuntimeException("Error retrieving balance for address: " + address, ex);
                                         })
                                         .join();

            return BalanceDTO.builder()
                             .tokenId(TOKEN_ID)
                             .balance(convertWeiToEth(weiBalance))
                             .build();

        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while retrieving balance", e);
        }
    }

    /**
     * Checks the status of the transaction with the given transaction hash
     *
     * @param transactionHash Transaction hash to check the status for
     * @return TransactionDTO containing the transaction details
     */
    public TransactionDTO checkTransactionStatus(String transactionHash) {
        logger.info("Checking transaction status for hash: {}", transactionHash);

        try {
            TransactionReceipt transactionReceipt = getTransactionReceipt(transactionHash);

            Transaction transaction = transactionRepository.findByTxHead(transactionHash).orElseThrow(
                    () -> new RuntimeException("Transaction not found for hash: " + transactionHash));

            if (transaction.getStatus().getCode().equals(PENDING.toString())) {
                transaction.setStatus(TransactionStatus.builder()
                                                       .code(transactionReceipt.isStatusOK() ? SUCCESS.toString() : FAILED.toString())
                                                       .build());
                transactionRepository.save(transaction);
            }

            logger.info("Transaction status: {}", transaction.getStatus().getCode());
            return transactionMapper.map(transaction);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while checking transaction status", e);
        }
    }

    /**
     * Retrieves the transaction receipt for the given transaction hash
     *
     * @param transactionHash Transaction hash to retrieve the receipt for
     * @return TransactionReceipt containing the transaction details
     */
    private TransactionReceipt getTransactionReceipt(String transactionHash) {
        try {
            return web3j.ethGetTransactionReceipt(transactionHash)
                        .sendAsync()
                        .thenApply(result -> {
                            if (result.getTransactionReceipt().isPresent()) {
                                return result.getTransactionReceipt().get();
                            } else {
                                throw new RuntimeException("Transaction receipt not found for hash: " + transactionHash);
                            }
                        })
                        .exceptionally(ex -> {
                            throw new RuntimeException("Error retrieving transaction receipt", ex);
                        })
                        .join();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while retrieving transaction receipt", e);
        }
    }

    /**
     * Executes a transaction from the given address to the given address with the given amount
     *
     * @param fromAddress Ethereum address to send the transaction from
     * @param secret      Secret key for the fromAddress
     * @param toAddress   Ethereum address to send the transaction to
     * @param amount      Amount to send in ETH
     * @return TransactionDTO containing the transaction details
     */
    public TransactionDTO executeTransaction(String fromAddress, String secret, String toAddress, BigDecimal amount) {
        logger.info("Executing transaction from: {} to: {} with amount: {} in {}", fromAddress, toAddress, amount, TOKEN_ID);

        try {
            Credentials credentials = Credentials.create(secret);
            TransactionManager transactionManager = new RawTransactionManager(web3j, credentials);

            String transactionHash = transactionManager
                                             .sendTransaction(
                                                     toWei("20", GWEI).toBigInteger(),
                                                     BigInteger.valueOf(21_000),
                                                     toAddress,
                                                     "",
                                                     toWei(amount, ETHER).toBigInteger())
                                             .getTransactionHash();

            if (transactionHash == null) {
                throw new RuntimeException("Error executing transaction");
            }

            logger.info("Transaction hash: {}", transactionHash);

            Transaction transaction = transactionRepository.save(
                    Transaction.builder()
                               .txHead(transactionHash)
                               .fromAddress(fromAddress)
                               .toAddress(toAddress)
                               .tokenId(TOKEN_ID)
                               .amount(amount)
                               .status(TransactionStatus.builder()
                                                        .code(PENDING.toString())
                                                        .build())
                               .build());

            return transactionMapper.map(transaction);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while executing transaction", e);
        }
    }
}
