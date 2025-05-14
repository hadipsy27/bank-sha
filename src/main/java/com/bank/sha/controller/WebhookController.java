package com.bank.sha.controller;

import com.bank.sha.dto.request.WebhookRequest;
import com.bank.sha.handler.ResponseHandler;
import com.bank.sha.service.WebhookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/webhook")
public class WebhookController {

    private WebhookService webhookService;

    @PostMapping("/transaction")
    public ResponseEntity<Object> transactionCallBack(@RequestBody WebhookRequest request) {
        Object response = webhookService.updateTransaction(request);

        return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
    }

    @GetMapping("/payment/finish")
    public String processPayment(@RequestParam String order_id, Model model) {


        model.addAttribute("transactionId", order_id);

        return "finish_payment";
    }

}
