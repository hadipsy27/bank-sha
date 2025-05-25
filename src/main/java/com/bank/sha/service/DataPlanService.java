package com.bank.sha.service;

import com.bank.sha.dto.request.DataPlanStoreRequest;
import com.bank.sha.entity.*;
import com.bank.sha.entity.enumEntity.Status;
import com.bank.sha.repository.*;
import com.bank.sha.util.PinUtil;
import com.bank.sha.util.RandomStringGeneratorUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DataPlanService {

    private UserRepository userRepository;
    private TransactionTypeRepository transactionTypeRepository;
    private TransactionRepository transactionRepository;
    private PaymentMethodRepository paymentMethodRepository;
    private WalletRepository walletRepository;
    private DataPlanRepository dataPlanRepository;
    private DataPlanHistoryRepository dataPlanHistoryRepository;
    private PinUtil pinUtil;

    private String store(DataPlanStoreRequest request, Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        TransactionType transactionType = transactionTypeRepository.findFirstByCode("internet").orElseThrow(() -> new EntityNotFoundException("TransactionType not found"));
        PaymentMethod paymentMethod = paymentMethodRepository.findFirstByCode("bwa").orElseThrow(() -> new EntityNotFoundException("PaymentMethod not found"));
        Wallet userWallet = walletRepository.findFirstByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException("Wallet not found"));
        DataPlan dataPlanById = dataPlanRepository.findById(Long.valueOf(request.getDataPlanId())).orElseThrow(() -> new EntityNotFoundException("DataPlan not found"));

        // PIN Checker
        boolean pinChecker = pinUtil.pinChecker(request.getPin(), user.getId());
        if (!pinChecker) throw new BadRequestException("Your PIN is wrong");

        // Check user balance
        if (userWallet.getBalance().compareTo(dataPlanById.getPrice()) < 0) {
            throw new BadRequestException("Your balance is not enough");
        }

        String transactionCode = RandomStringGeneratorUtil.generateRandomUppercaseStringLetterAndNumber(10);
        Transaction transaction = Transaction.builder()
                .userId(User.builder().id(user.getId()).build())
                .transactionTypeId(TransactionType.builder().id(transactionType.getId()).build())
                .paymentMethodId(PaymentMethod.builder().id(paymentMethod.getId()).build())
                .amount(dataPlanById.getPrice())
                .transactionCode(transactionCode)
                .description("Insert data plan " + dataPlanById.getName())
                .status(Status.SUCCESS)
                .build();
        transactionRepository.save(transaction);

        DataPlanHistory dataPlanHistory = DataPlanHistory.builder()
                .dataPlan(DataPlan.builder().id(Long.valueOf(request.getDataPlanId())).build())
                .transaction(Transaction.builder().id(transaction.getId()).build())
                .phoneNumber(request.getPhoneNumber())
                .build();
        dataPlanHistoryRepository.save(dataPlanHistory);

        Wallet wallet = Wallet.builder().id(userWallet.getId()).balance(userWallet.getBalance().add(dataPlanById.getPrice())).build();
        walletRepository.save(wallet);

        return "Success buy paket data";
    }

}
