package com.bank.sha.repository;

import com.bank.sha.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.wallets w WHERE w.user.id = u.id AND u.email = :email")
    Optional<User> findByUserWithWallet(@Param("email") String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.wallets w WHERE w.user.id = u.id AND u.email = COALESCE(:email, null) OR w.cardNumber = COALESCE(:cardNumber, null) ")
    Optional<User> findByUserWithWalletByEmailAndCardNumber(@Param("email") String email, @Param("cardNumber") String cardNumber);

}
