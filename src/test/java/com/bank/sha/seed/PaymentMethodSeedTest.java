package com.bank.sha.seed;

import com.bank.sha.entity.PaymentMethods;
import com.bank.sha.entity.enumEntity.Status;
import com.bank.sha.repository.PaymentMethodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentMethodSeedTest {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Test
    public void testAddPaymentMethod() {

        PaymentMethods save = PaymentMethods.builder()
                .name("Bank BWA")
                .code("bwa")
                .thumbnail("thumbnail2.jpg")
                .status(Status.valueOf("ACTIVE"))
                .build();

        paymentMethodRepository.save(save);

        save = PaymentMethods.builder()
                .name("Bank BNI")
                .code("bni")
                .thumbnail("thumbnail3.jpg")
                .status(Status.valueOf("ACTIVE"))
                .build();

        paymentMethodRepository.save(save);

        save = PaymentMethods.builder()
                .name("Bank BRI")
                .code("bri")
                .thumbnail("thumbnail4.jpg")
                .status(Status.valueOf("ACTIVE"))
                .build();

        paymentMethodRepository.save(save);


        System.out.println("Saved Payment Method: " + paymentMethodRepository);
    }
}
