package com.bank.sha.repository;

import com.bank.sha.entity.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethods, Long> {
}
