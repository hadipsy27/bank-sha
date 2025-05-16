package com.bank.sha.repository;

import com.bank.sha.entity.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
}
