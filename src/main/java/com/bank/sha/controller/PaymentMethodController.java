package com.bank.sha.controller;

import com.bank.sha.handler.ResponseHandler;
import com.bank.sha.service.PaymentMethodService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment-method")
@AllArgsConstructor
@Slf4j
public class PaymentMethodController {

    private PaymentMethodService paymentMethodService;

    @RequestMapping()
    public ResponseEntity<Object> getListPaymentMethod() {
        Object response = paymentMethodService.getPaymentMethods();
        return ResponseHandler.generateResponse("Success get list Payment Method", HttpStatus.OK, response);
    }

}
