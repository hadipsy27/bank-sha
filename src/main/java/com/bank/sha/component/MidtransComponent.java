package com.bank.sha.component;

import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.service.MidtransSnapApi;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MidtransComponent {

    @Value("${midtrans.server.key}")
    private String midtransServerKey;

    @Value("${midtrans.production}")
    private boolean isProduction;

    @Getter
    private MidtransSnapApi snapApi;

    @PostConstruct
    public void init() {
        Config snapConfigOptions = Config.builder()
                .setServerKey(midtransServerKey)
                .setIsProduction(isProduction)
                .build();

        this.snapApi = new ConfigFactory(snapConfigOptions).getSnapApi();
    }

}
