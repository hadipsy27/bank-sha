package com.bank.sha.seed;

import com.bank.sha.entity.PaymentMethod;
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

        PaymentMethod save = PaymentMethod.builder()
                .name("Bank BWA")
                .code("bwa_va")
                .thumbnail("bwa.jpg")
                .status(Status.valueOf("ACTIVE"))
                .build();

        paymentMethodRepository.save(save);

        save = PaymentMethod.builder()
                .name("Bank BNI")
                .code("bni_va")
                .thumbnail("bni.jpg")
                .status(Status.valueOf("ACTIVE"))
                .build();

        paymentMethodRepository.save(save);

        save = PaymentMethod.builder()
                .name("Bank BRI")
                .code("bri_va")
                .thumbnail("bri.jpg")
                .status(Status.valueOf("ACTIVE"))
                .build();

        paymentMethodRepository.save(save);

        save = PaymentMethod.builder()
                .name("Bank BCA")
                .code("bca_va")
                .thumbnail("bca.jpg")
                .status(Status.valueOf("ACTIVE"))
                .build();

        paymentMethodRepository.save(save);


        System.out.println("Saved Payment Method: " + paymentMethodRepository);
    }
}
