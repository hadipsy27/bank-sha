package com.bank.sha.controller;

import com.bank.sha.dto.request.StoreDto;
import com.bank.sha.entity.UserPrincipal;
import com.bank.sha.service.TopUpService;
import com.bank.sha.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/topup")
@AllArgsConstructor
@Validated
public class TopUpController {

    private TopUpService topUpService;

    @RequestMapping("/store")
    public ResponseEntity<Object> store(@RequestBody @Valid StoreDto storeDto, @AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        log.info("User Id : {}", userPrincipal.getUser().getId());
        Object store = topUpService.store(storeDto, userPrincipal.getUser().getId());
        return ResponseUtil.generateResponse("Success", HttpStatus.OK, store);
    }
}
