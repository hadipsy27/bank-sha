package com.bank.sha.service;

import com.bank.sha.dto.request.StoreDto;
import com.bank.sha.dto.response.StoreResponse;
import com.bank.sha.entity.PaymentMethod;
import com.bank.sha.entity.Transaction;
import com.bank.sha.entity.TransactionType;
import com.bank.sha.entity.User;
import com.bank.sha.entity.enumEntity.Status;
import com.bank.sha.repository.PaymentMethodRepository;
import com.bank.sha.repository.TransactionRepository;
import com.bank.sha.repository.TransactionTypeRepository;
import com.bank.sha.util.PinUtil;
import com.bank.sha.util.RandomStringGeneratorUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TopUpService {

    private TransactionTypeRepository transactionTypeRepository;
    private PaymentMethodRepository paymentMethodRepository;
    private TransactionRepository transactionRepository;
    private PinUtil pinUtil;
    private static final String TOP_UP_TRANSACTION_TYPE_CODE = "top_up";

    @Transactional
    public Object store(StoreDto storeDto, Long userId) throws Exception {

        boolean pinChecker = pinUtil.pinChecker(storeDto.getPin(), userId);
        if (!pinChecker) throw new BadRequestException("Your PIN is wrong");

        Optional<TransactionType> transactionType = transactionTypeRepository.findFirstByCode(TOP_UP_TRANSACTION_TYPE_CODE);
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findFirstByCode(storeDto.getPaymentMethodCode());
        if (transactionType.isEmpty() || paymentMethod.isEmpty()) throw new BadRequestException("Transaction type or payment method not found");

        Transaction transaction = Transaction.builder()
                .userId(User.builder().id(userId).build())
                .paymentMethodId(PaymentMethod.builder().id(paymentMethod.get().getId()).build())
                .transactionTypeId(TransactionType.builder().id(transactionType.get().getId()).build())
                .amount(BigDecimal.valueOf(storeDto.getAmount()))
                .transactionCode(RandomStringGeneratorUtil.generateRandomUppercaseStringLetterAndNumber(10))
                .description("Top up via " + paymentMethod.get().getName() + " with amount " + storeDto.getAmount())
                .status(Status.PENDING)
                .build();
        transactionRepository.save(transaction);

        return StoreResponse.builder()
                .id(transaction.getId())
                .userId(transaction.getUserId().getId())
                .transactionTypeId(transaction.getTransactionTypeId().getId())
                .paymentMethodId(transaction.getPaymentMethodId().getId())
                .productId(transaction.getProductId() != null ? transaction.getProductId().getId() : null)
                .amount(transaction.getAmount())
                .transactionCode(transaction.getTransactionCode())
                .description(transaction.getDescription())
                .status(String.valueOf(transaction.getStatus()))
                .build();
    }
}
