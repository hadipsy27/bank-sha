package com.bank.sha.seed;

import com.bank.sha.entity.DataPlan;
import com.bank.sha.repository.DataPlanRepository;
import com.bank.sha.repository.OperatorCardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class DataPlanSeedTest {

    @Autowired
    private DataPlanRepository dataPlanRepository;

    @Autowired
    OperatorCardRepository operatorCardRepository;

    @Test
    public void testAddDataPlan() {
        DataPlan dataPlan = new DataPlan();
        dataPlan.setName("10 GB");
        dataPlan.setPrice(BigDecimal.valueOf(100));
        dataPlan.setOperatorCard(operatorCardRepository.findByName("Telkomsel"));
        dataPlanRepository.save(dataPlan);

        dataPlan = new DataPlan();
        dataPlan.setName("50 GB");
        dataPlan.setPrice(BigDecimal.valueOf(500));
        dataPlan.setOperatorCard(operatorCardRepository.findByName("Telkomsel"));
        dataPlanRepository.save(dataPlan);

        dataPlan = new DataPlan();
        dataPlan.setName("100 GB");
        dataPlan.setPrice(BigDecimal.valueOf(1000));
        dataPlan.setOperatorCard(operatorCardRepository.findByName("Telkomsel"));
        dataPlanRepository.save(dataPlan);

        dataPlan = new DataPlan();
        dataPlan.setName("10 GB");
        dataPlan.setPrice(BigDecimal.valueOf(2000));
        dataPlan.setOperatorCard(operatorCardRepository.findByName("Indosat"));
        dataPlanRepository.save(dataPlan);

        dataPlan = new DataPlan();
        dataPlan.setName("50 GB");
        dataPlan.setPrice(BigDecimal.valueOf(4000));
        dataPlan.setOperatorCard(operatorCardRepository.findByName("Indosat"));
        dataPlanRepository.save(dataPlan);

        dataPlan = new DataPlan();
        dataPlan.setName("100 GB");
        dataPlan.setPrice(BigDecimal.valueOf(6000));
        dataPlan.setOperatorCard(operatorCardRepository.findByName("Indosat"));
        dataPlanRepository.save(dataPlan);

        dataPlan = new DataPlan();
        dataPlan.setName("10 GB");
        dataPlan.setPrice(BigDecimal.valueOf(8000));
        dataPlan.setOperatorCard(operatorCardRepository.findByName("XL"));
        dataPlanRepository.save(dataPlan);

        dataPlan = new DataPlan();
        dataPlan.setName("50 GB");
        dataPlan.setPrice(BigDecimal.valueOf(16000));
        dataPlan.setOperatorCard(operatorCardRepository.findByName("XL"));
        dataPlanRepository.save(dataPlan);

        dataPlan = new DataPlan();
        dataPlan.setName("100 GB");
        dataPlan.setPrice(BigDecimal.valueOf(24000));
        dataPlan.setOperatorCard(operatorCardRepository.findByName("XL"));
        dataPlanRepository.save(dataPlan);

        System.out.println("Saved Data Plan: " + dataPlanRepository);
    }
}
