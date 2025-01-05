package com.bank.sha.repository;

import com.bank.sha.entity.OperatorCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorCardRepository extends JpaRepository<OperatorCard, Long> {

    OperatorCard findByName(String name);
}
