package com.cleeviox.interview.repository;

import com.cleeviox.interview.model.Transaction;
import com.cleeviox.interview.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    public Optional<Transaction> findByTxHead(String txHead);

    public List<Transaction> findAllByStatus(TransactionStatus status);

}
