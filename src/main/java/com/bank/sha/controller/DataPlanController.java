package com.bank.sha.controller;

import com.bank.sha.dto.request.DataPlanStoreRequest;
import com.bank.sha.entity.UserPrincipal;
import com.bank.sha.handler.ResponseHandler;
import com.bank.sha.service.DataPlanService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/dataplan")
public class DataPlanController {

    private DataPlanService dataPlanService;

    @RequestMapping("/store")
    public ResponseEntity<Object> store(@RequestBody @Valid DataPlanStoreRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal)
            throws Exception {
        log.info("User Id : {}", userPrincipal.getUser().getId());
        Object response = dataPlanService.store(request, userPrincipal.getUser().getId());
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, response);
    }


}
