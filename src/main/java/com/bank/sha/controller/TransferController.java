package com.bank.sha.controller;

import com.bank.sha.dto.request.TransferBwaDto;
import com.bank.sha.dto.response.TransferBwaResponseDto;
import com.bank.sha.entity.UserPrincipal;
import com.bank.sha.handler.ResponseHandler;
import com.bank.sha.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transfer")
public class TransferController {

    private TransferService transferService;

    @PostMapping("/bwa")
    public ResponseEntity<Object> transferBankWolesAja(@RequestBody TransferBwaDto request, @AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        TransferBwaResponseDto response = transferService.transferBankWolesAja(request, userPrincipal.getUser().getId());
        return ResponseHandler.generateResponse("Success transfer", HttpStatus.OK, response);
    }
}
