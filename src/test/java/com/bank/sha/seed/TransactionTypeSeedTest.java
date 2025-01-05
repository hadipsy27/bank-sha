package com.bank.sha.seed;

import com.bank.sha.entity.TransactionType;
import com.bank.sha.repository.TransactionTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionTypeSeedTest {

    @Autowired
    TransactionTypeRepository transactionTypeRepository;

    @Test
    public void testAddTransactionType() {
        TransactionType transactionType = new TransactionType();
        transactionType.setName("Transfer");
        transactionType.setCode("transfer");
        transactionType.setAction("dr");
        transactionType.setThumbnail("transfer.jpg");
        transactionTypeRepository.save(transactionType);

        transactionType = new TransactionType();
        transactionType.setName("Internet");
        transactionType.setCode("internet");
        transactionType.setAction("dr");
        transactionType.setThumbnail("internet.jpg");
        transactionTypeRepository.save(transactionType);

        transactionType = new TransactionType();
        transactionType.setName("Top up");
        transactionType.setCode("top_up");
        transactionType.setAction("cr");
        transactionType.setThumbnail("top_up.jpg");
        transactionTypeRepository.save(transactionType);

        transactionType = new TransactionType();
        transactionType.setName("Receive");
        transactionType.setCode("receive");
        transactionType.setAction("cr");
        transactionType.setThumbnail("receive.jpg");
        transactionTypeRepository.save(transactionType);

        transactionType = new TransactionType();
        transactionType.setName("Withdraw");
        transactionType.setCode("withdraw");
        transactionType.setAction("dr");
        transactionType.setThumbnail("withdraw.jpg");
        transactionTypeRepository.save(transactionType);

        System.out.println("Saved Transaction Type: " + transactionTypeRepository);
    }
}
