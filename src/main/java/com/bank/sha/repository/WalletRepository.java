package com.bank.sha.repository;

import com.bank.sha.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findFirstByUserId(Long userId);

}
