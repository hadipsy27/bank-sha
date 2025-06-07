package com.bank.sha.service;

import com.bank.sha.dto.response.operatorCardResponse.DataPlanResponse;
import com.bank.sha.dto.response.operatorCardResponse.OperatorCardResponse;
import com.bank.sha.entity.OperatorCard;
import com.bank.sha.repository.OperatorCardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OperatorCardService {

    private OperatorCardRepository operatorCardRepository;
    private ServerProperties serverProperties;

    private final static String host = "http://localhost";

    @Transactional(readOnly = true)
    public Page<OperatorCardResponse> getAllOperatorCardsWithPlans(int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        Page<OperatorCard> pageResult = operatorCardRepository.findAllBasic(pageable);

        List<OperatorCardResponse> content = pageResult.stream()
                .map(card -> new OperatorCardResponse(
                        card.getId(),
                        card.getName(),
                        host + ":" + serverProperties.getPort().toString() + "/dataplan/" + card.getThumbnail(),
                        card.getStatus(),
                        card.getDataPlans().stream()
                                .map(plan -> new DataPlanResponse(
                                        plan.getId(),
                                        plan.getName(),
                                        plan.getPrice()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, pageResult.getTotalElements());
    }

}