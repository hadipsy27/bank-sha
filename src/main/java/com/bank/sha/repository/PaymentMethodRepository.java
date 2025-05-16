package com.bank.sha.repository;

import com.bank.sha.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    
    Optional<PaymentMethod> findFirstByCode(String code);

    PaymentMethod getPaymentMethodByCode(String code);
}
