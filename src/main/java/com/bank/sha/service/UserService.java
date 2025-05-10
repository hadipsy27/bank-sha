package com.bank.sha.service;

import com.bank.sha.entity.User;
import com.bank.sha.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserAndWalletByEmail(String email){
        return userRepository.findByUserWithWallet(email).orElseThrow(() -> new RuntimeException("User not found"));
    }


}
