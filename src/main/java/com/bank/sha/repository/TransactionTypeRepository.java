package com.bank.sha.repository;

import com.bank.sha.entity.TransactionType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
    
    Optional<TransactionType> findFirstByCode(String code);

    List<TransactionType> findByCodeIn(Collection<String> codes, Sort code);
}
