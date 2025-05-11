package com.bank.sha.util;

import com.bank.sha.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PinUtil {

    private WalletRepository walletRepository;

    public boolean pinChecker(String pin, Long userId){
        return walletRepository.findFirstByUserId(userId)
                .map(wallet -> wallet.getPin().equals(pin))
                .orElse(false);
    }


}
