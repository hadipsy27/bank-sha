package com.bank.sha.repository;

import com.bank.sha.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findFirstByTransactionCode(String transactionCode);
}
