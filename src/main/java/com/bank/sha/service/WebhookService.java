package com.bank.sha.service;

import com.bank.sha.dto.request.WebhookRequest;
import com.bank.sha.entity.Transaction;
import com.bank.sha.entity.Wallet;
import com.bank.sha.entity.enumEntity.Status;
import com.bank.sha.handler.TransactionStatusHandler;
import com.bank.sha.repository.TransactionRepository;
import com.bank.sha.repository.WalletRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class WebhookService {

    private WalletRepository walletRepository;
    private TransactionRepository transactionRepository;

    public Object updateTransaction(WebhookRequest request){

        String transactionCode = request.getOrder_id();
        String transactionStatus = request.getTransaction_status();
        String fraudStatus = request.getFraud_status();
        BigDecimal amount = request.getGross_amount();

        log.info("Transaction notification received. Order ID: {}. Transaction status: {}. Fraud status: {}",
                transactionCode, transactionStatus, fraudStatus);

        String status = TransactionStatusHandler.transactionStatusHandling(transactionStatus, fraudStatus);

        Transaction getTransactionByCode = transactionRepository.findFirstByTransactionCode(transactionCode);
        if (!getTransactionByCode.getStatus().equals(Status.SUCCESS)){
            if(status != null && status.equals("success")){
                getTransactionByCode.setAmount(amount);
                getTransactionByCode.setStatus(Status.valueOf(status.toUpperCase()));
                transactionRepository.save(getTransactionByCode);

                Optional<Wallet> wallet = walletRepository.findFirstByUserId(getTransactionByCode.getUserId().getId());
                if (wallet.isPresent()){
                    Wallet getWallet = wallet.get();
                    BigDecimal balance = getWallet.getBalance();
                    BigDecimal totalBalance = balance.add(amount);
                    getWallet.setBalance(totalBalance);
                    walletRepository.save(getWallet);
                    log.info("Transaction from:, amount: status:, total balance:  {}, {}, {}, {}" , wallet.get().getUser().getName(), amount, status, totalBalance);
                }
            }
        } else {
            status = "Tidak dapat memproses transaksi karena sudah berhasil. Status transaksi: " + getTransactionByCode.getStatus();
        }

        return Map.<String, Object>of("order_id", transactionCode, "status", status);
    }


}
