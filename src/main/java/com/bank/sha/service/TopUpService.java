package com.bank.sha.service;

import com.bank.sha.component.MidtransConfiguration;
import com.bank.sha.dto.request.StoreDto;
import com.bank.sha.entity.PaymentMethod;
import com.bank.sha.entity.Transaction;
import com.bank.sha.entity.TransactionType;
import com.bank.sha.entity.User;
import com.bank.sha.entity.enumEntity.Status;
import com.bank.sha.repository.PaymentMethodRepository;
import com.bank.sha.repository.TransactionRepository;
import com.bank.sha.repository.TransactionTypeRepository;
import com.bank.sha.repository.UserRepository;
import com.bank.sha.util.PinUtil;
import com.bank.sha.util.RandomStringGeneratorUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TopUpService {

    private UserRepository userRepository;
    private TransactionTypeRepository transactionTypeRepository;
    private PaymentMethodRepository paymentMethodRepository;
    private TransactionRepository transactionRepository;
    private PinUtil pinUtil;
    private MidtransConfiguration midtransConfiguration;
    private static final String TOP_UP_TRANSACTION_TYPE_CODE = "top_up";

    @Transactional
    public Object store(StoreDto storeDto, Long userId) throws Exception {

        // PIN Checker
        boolean pinChecker = pinUtil.pinChecker(storeDto.getPin(), userId);
        if (!pinChecker) throw new BadRequestException("Your PIN is wrong");

        // Get Transaction Type and Payment Method
        Optional<TransactionType> transactionType = transactionTypeRepository.findFirstByCode(TOP_UP_TRANSACTION_TYPE_CODE);
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findFirstByCode(storeDto.getPaymentMethodCode());
        if (transactionType.isEmpty() || paymentMethod.isEmpty()) throw new BadRequestException("Transaction type or payment method not found");

        // Build a Transaction model
        Transaction transaction = Transaction.builder()
                .userId(User.builder().id(userId).build())
                .paymentMethodId(PaymentMethod.builder().id(paymentMethod.get().getId()).build())
                .transactionTypeId(TransactionType.builder().id(transactionType.get().getId()).build())
                .amount(BigDecimal.valueOf(storeDto.getAmount()))
                .transactionCode(RandomStringGeneratorUtil.generateRandomUppercaseStringLetterAndNumber(10))
                .description("Top up via " + paymentMethod.get().getName() + " with amount " + storeDto.getAmount())
                .status(Status.PENDING)
                .build();

        // transaction_details
        Map<String, String> transDetail = new HashMap<>();
        transDetail.put("order_id", transaction.getTransactionCode());
        transDetail.put("gross_amount", transaction.getAmount().toString());

        // customer_details
        Optional<User> user = userRepository.findById(userId);
        Map<String, String> customerDetail = new HashMap<>();
        customerDetail.put("first_name", user.get().getName().split(" ")[0]);
        customerDetail.put("last_name", user.get().getName().split(" ").length > 1 ? user.get().getName().split(" ")[1] : "");
        customerDetail.put("email", user.get().getEmail());

        // credit_card
        Map<String, String> creditCard = new HashMap<>();
        creditCard.put("secure", "true");

        // enabled_payments
        List<String> enablePayment = List.of(storeDto.getPaymentMethodCode());

        // Build Body Request to midtrans
        Map<String, Object> body = new HashMap<>();
        body.put("transaction_details", transDetail);
        body.put("customer_details", customerDetail);
        body.put("credit_card", creditCard);
        body.put("enabled_payments", enablePayment);

        // Hit to SnapApi midtrans
        JSONObject snapTransaction = midtransConfiguration.midtransSnapApi().createTransaction(body);
        Map<String, String> result = new HashMap<>();
        result.put("token", snapTransaction.getString("token"));
        result.put("redirect_url", snapTransaction.getString("redirect_url"));

        // Save Transaction
        transactionRepository.save(transaction);
        return result;
    }
}
