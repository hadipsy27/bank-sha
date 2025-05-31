package com.bank.sha.repository;

import com.bank.sha.entity.OperatorCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OperatorCardRepository extends JpaRepository<OperatorCard, Long> {

    OperatorCard findByName(String name);

    @Query(value = "SELECT o FROM OperatorCard o",
            countQuery = "SELECT count(o) FROM OperatorCard o")
    Page<OperatorCard> findAllBasic(Pageable pageable);
}
