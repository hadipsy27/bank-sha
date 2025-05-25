package com.bank.sha.service;

import com.bank.sha.dto.request.TransferBwaDto;
import com.bank.sha.dto.response.TransferBwaResponseDto;
import com.bank.sha.entity.*;
import com.bank.sha.entity.enumEntity.Status;
import com.bank.sha.repository.*;
import com.bank.sha.util.RandomStringGeneratorUtil;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class TransferService {

    private final TransactionRepository transactionRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransferHistoryRepository transferHistoryRepository;
    private UserRepository userRepository;
    private WalletRepository walletRepository;

    public TransferBwaResponseDto transferBankWolesAja(TransferBwaDto transferBwaDto, Long userId) throws Exception {
        User recipient = userRepository.findByUserWithWalletByEmailOrCardNumber(
                        transferBwaDto.getSendTo(),
                        transferBwaDto.getSendTo())
                .orElseThrow(() -> new BadRequestException("Recipient not found"));

        Wallet senderWallet = walletRepository.findByUserId(userId);
        if (senderWallet == null) throw new BadRequestException("Wallet user is not found");
        if (!senderWallet.getPin().equals(transferBwaDto.getPin())) throw new BadRequestException("Your PIN is wrong");
        if (senderWallet.getId().equals(recipient.getWallets().get(0).getId()))
            throw new BadRequestException("You cannot transfer to your own wallet");
        if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(transferBwaDto.getAmount())) < 0)
            throw new BadRequestException("Your balance is not enough");

        List<TransactionType> transactionType = transactionTypeRepository.findByCodeIn(List.of("receive", "transfer"), Sort.by(Sort.Direction.ASC, "code"));
        Long receiveTransactionTypeId = transactionType.get(0).getId();
        Long transferTransactionTypeId = transactionType.get(1).getId();

        PaymentMethod paymentMethod = paymentMethodRepository.getPaymentMethodByCode("bwa"); // Bank Woles Aja
        User userTransfer = userRepository.findById(userId).orElseThrow(() -> new BadRequestException("User not found"));
        String transactionCode = RandomStringGeneratorUtil.generateRandomUppercaseStringLetterAndNumber(10);

        // Update recipient Wallet
        Transaction receiveTransaction = Transaction.builder()
                .userId(User.builder().id(recipient.getId()).build())
                .transactionTypeId(TransactionType.builder().id(receiveTransactionTypeId).build())
                .description("Receive from " + userTransfer.getName() + " via " + paymentMethod.getName() + " with amount " + transferBwaDto.getAmount())
                .amount(BigDecimal.valueOf(transferBwaDto.getAmount()))
                .transactionCode(transactionCode)
                .status(Status.valueOf("SUCCESS"))
                .paymentMethodId(PaymentMethod.builder().id(paymentMethod.getId()).build())
                .build();

        Wallet recipientWallet = walletRepository.findFirstByUserId(recipient.getId()).get();
        BigDecimal balanceRecipientWallet = recipientWallet.getBalance();
        recipientWallet.setBalance(balanceRecipientWallet.add(BigDecimal.valueOf(transferBwaDto.getAmount())));
        walletRepository.save(recipientWallet);
        transactionRepository.save(receiveTransaction);

        // Update sender Wallet
        Transaction transferTransaction = Transaction.builder()
                .userId(User.builder().id(userId).build())
                .transactionTypeId(TransactionType.builder().id(transferTransactionTypeId).build())
                .description("Transfer to " + recipient.getName() + " via " + paymentMethod.getName() + " with amount " + transferBwaDto.getAmount())
                .amount(BigDecimal.valueOf(transferBwaDto.getAmount()))
                .transactionCode(transactionCode)
                .status(Status.valueOf("SUCCESS"))
                .paymentMethodId(PaymentMethod.builder().id(paymentMethod.getId()).build())
                .build();

        BigDecimal balanceSender = senderWallet.getBalance();
        senderWallet.setBalance(balanceSender.subtract(BigDecimal.valueOf(transferBwaDto.getAmount())));
        transactionRepository.save(transferTransaction);
        walletRepository.save(senderWallet);

        // Transfer History
        TransferHistory transferHistory = TransferHistory.builder()
                .senderId(User.builder().id(userId).build())
                .receiverId(User.builder().id(recipient.getId()).build())
                .description("Transfer from " + senderWallet.getUser().getName() + " to " + recipient.getName() + " via " + paymentMethod.getName() + " with amount " + transferBwaDto.getAmount())
                .transactionCode(transactionCode)
                .build();
        transferHistoryRepository.save(transferHistory);

        return TransferBwaResponseDto.builder()
                .recipientName(recipient.getName())
                .recipientCardNumber(recipientWallet.getCardNumber())
                .transactionCode(transactionCode)
                .amount(transferBwaDto.getAmount())
                .status(String.valueOf(transferTransaction.getStatus()))
                .build();
    }

}
