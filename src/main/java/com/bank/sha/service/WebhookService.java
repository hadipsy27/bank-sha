package com.bank.sha.service;

import com.bank.sha.dto.request.WebhookRequest;
import com.bank.sha.entity.Transaction;
import com.bank.sha.entity.enumEntity.Status;
import com.bank.sha.handler.TransactionStatusHandler;
import com.bank.sha.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class WebhookService {

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
            }
        }

        return status;
    }


}
