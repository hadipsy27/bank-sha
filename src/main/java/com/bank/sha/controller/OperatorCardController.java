package com.bank.sha.controller;

import com.bank.sha.dto.response.operatorCardResponse.OperatorCardResponse;
import com.bank.sha.handler.PaginatedResponse;
import com.bank.sha.service.OperatorCardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/operatorcard")
@AllArgsConstructor
public class OperatorCardController {

    private OperatorCardService operatorCardService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<OperatorCardResponse>> getOperatorCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws Exception {

        Page<OperatorCardResponse> resultPage = operatorCardService.getAllOperatorCardsWithPlans(page, size);

        PaginatedResponse<OperatorCardResponse> response = new PaginatedResponse<>(
                HttpStatus.OK,
                "Success get operator cards",
                resultPage.getContent(),
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements(),
                resultPage.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

}
