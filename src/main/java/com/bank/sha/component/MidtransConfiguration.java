package com.bank.sha.component;

import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.service.MidtransSnapApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MidtransConfiguration {

    @Value("${midtrans.server.key}")
    private String midtransServerKey;

    @Value("${midtrans.production}")
    private boolean isProduction;


    @Bean
    public MidtransSnapApi midtransSnapApi() {
        Config snapConfigOptions = Config.builder()
                .setServerKey(midtransServerKey)
                .setIsProduction(isProduction)
                .build();

         return new ConfigFactory(snapConfigOptions).getSnapApi();
    }

}
