package com.bank.sha.service;

import com.bank.sha.dto.RegisterUserDto;
import com.bank.sha.entity.User;
import com.bank.sha.entity.Wallet;
import com.bank.sha.entity.enumEntity.Status;
import com.bank.sha.repository.UserRepository;
import com.bank.sha.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private static final String IMAGE_DIRECTORY = "src/main/resources/static/images/";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;

    public String registerUser(RegisterUserDto registerUserDto) {
        try {
            // Check if user already exists
            User findByEmail = userRepository.findByEmail(registerUserDto.getEmail());
            if (findByEmail != null) {
                return "User already exists";
            }

            User finByUsername = userRepository.findByUsername(registerUserDto.getUsername());
            if (finByUsername != null) {
                return "Username already exists";
            }

            // Save KTP Image
            String ktpFileName = registerUserDto.getEmail().toLowerCase() + "_ktp.jpg";
            saveBase64Image(registerUserDto.getKtp(), IMAGE_DIRECTORY + ktpFileName);

            // Save Profile Picture
            String ppFileName = registerUserDto.getEmail().toLowerCase() + "_profile_picture.jpg";
            saveBase64Image(registerUserDto.getProfilePicture(), IMAGE_DIRECTORY + ppFileName);

            // Save User
            User user = new User();
            user.setName(registerUserDto.getName());
            user.setEmail(registerUserDto.getEmail().toLowerCase());
            user.setPassword(registerUserDto.getPassword());
            user.setUsername(registerUserDto.getUsername());
            user.setKtp(ktpFileName);
            user.setProfilePicture(ppFileName);
            user.setVerified(true);

            // Save Wallet
            Wallet wallet = new Wallet();
            wallet.setUser(user);
            wallet.setBalance(BigDecimal.valueOf(0));
            wallet.setPin(registerUserDto.getPin());
            SecureRandom random = new SecureRandom();
            wallet.setCardNumber(String.valueOf((random.nextLong() & Long.MAX_VALUE) % 1_000_000_000_000_000L));

            userRepository.save(user);
            walletRepository.save(wallet);

            return "User registered successfully";
        } catch (Exception e) {
            return "Registration failed: " + e.getMessage();
        }
    }

    private void saveBase64Image(String base64String, String filePath) throws IOException {
        // Ensure directory exists
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (base64String == null || base64String.isEmpty()) {
            throw new IllegalArgumentException("Base64 string is empty");
        }
        byte[] imageBytes = Base64.getDecoder().decode(base64String);
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBytes);
        }
    }

}
