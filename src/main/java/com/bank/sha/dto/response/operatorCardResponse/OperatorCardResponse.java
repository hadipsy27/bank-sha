package com.bank.sha.dto.response.operatorCardResponse;

import com.bank.sha.entity.enumEntity.Status;

import java.util.List;


public record OperatorCardResponse(
        Long id,
        String name,
        String thumbnail,
        Status status,
        List<DataPlanResponse> dataPlans
) {}
