package com.bank.sha.service;

import com.bank.sha.dto.request.LoginUserDto;
import com.bank.sha.dto.request.RegisterUserDto;
import com.bank.sha.entity.User;
import com.bank.sha.entity.Wallet;
import com.bank.sha.repository.UserRepository;
import com.bank.sha.repository.WalletRepository;
import com.bank.sha.util.SaveImageUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private WalletRepository walletRepository;

    @Transactional
    public User registerUser(RegisterUserDto registerUserDto) throws Exception{
        try {
            // Check if a user already exists
            Optional<User> findByEmail = userRepository.findByEmail(registerUserDto.getEmail());
            if (findByEmail.isPresent()) {
                throw new RuntimeException("User already exists");
            }

            User finByUsername = userRepository.findByUsername(registerUserDto.getUsername());
            if (finByUsername != null) {
                throw new RuntimeException("Username already exists");
            }

            // Save KTP Image
            String ktpFileName = registerUserDto.getEmail().toLowerCase() + "_ktp.jpg";
            new SaveImageUtil().saveBase64Image(registerUserDto.getKtp(), ktpFileName);

            // Save Profile Picture
            String ppFileName = registerUserDto.getEmail().toLowerCase() + "_profile_picture.jpg";
            new SaveImageUtil().saveBase64Image(registerUserDto.getProfilePicture(), ppFileName);

            // Save User
            User user = User.builder()
                    .name(registerUserDto.getName())
                    .email(registerUserDto.getEmail().toLowerCase())
                    .password(passwordEncoder.encode(registerUserDto.getPassword()))
                    .username(registerUserDto.getUsername())
                    .ktp(ktpFileName)
                    .profilePicture(ppFileName)
                    .verified(true)
                    .build();

            // Save Wallet
            Wallet wallet = Wallet.builder()
                    .user(user)
                    .balance(BigDecimal.valueOf(0))
                    .pin(registerUserDto.getPin())
                    .cardNumber(String.valueOf((new SecureRandom().nextLong() & Long.MAX_VALUE) % 1_000_000_000_000_000L))
                    .build();

            user.setWallets(List.of(wallet));
            userRepository.save(user);
            walletRepository.save(wallet);

            return userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("Failed to retrieve saved user"));
        } catch (Exception e) {
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    public User authenticate(LoginUserDto input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
