package com.bank.sha.service;


import com.bank.sha.dto.response.PaymentMethodResponse;
import com.bank.sha.repository.PaymentMethodRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PaymentMethodService {

    private PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethodResponse> getPaymentMethods() {
        return paymentMethodRepository.findAll()
                .stream()
                .filter(paymentMethod -> !paymentMethod.getCode().equals("BWA"))
                .map(paymentMethod -> PaymentMethodResponse.builder()
                        .code(paymentMethod.getCode())
                        .name(paymentMethod.getName())
                        .thumbnail("/banks/" + paymentMethod.getThumbnail())
                        .status(String.valueOf(paymentMethod.getStatus()))
                        .build())
                .toList();
    }

}
