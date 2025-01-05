package com.bank.sha.seed;

import com.bank.sha.entity.OperatorCard;
import com.bank.sha.entity.enumEntity.Status;
import com.bank.sha.repository.OperatorCardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OperatorCardSeedTest {

    @Autowired
    private OperatorCardRepository operatorCardRepository;

    @Test
    public void testAddOperatorCard() {

        OperatorCard save = OperatorCard.builder()
                .name("Telkomsel")
                .status(Status.valueOf("ACTIVE"))
                .thumbnail("telkomsel.jpg")
                .build();
        operatorCardRepository.save(save);

        save = OperatorCard.builder()
                .name("Indosat")
                .status(Status.valueOf("ACTIVE"))
                .thumbnail("indosat.jpg")
                .build();
        operatorCardRepository.save(save);

        save = OperatorCard.builder()
                .name("MTN")
                .status(Status.valueOf("ACTIVE"))
                .thumbnail("mtn.jpg")
                .build();
        operatorCardRepository.save(save);

        System.out.println("Saved Operator Card: " + operatorCardRepository);
    }
}
