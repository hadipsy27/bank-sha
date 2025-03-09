package com.bank.sha.service;

import com.bank.sha.dto.RegisterUserDto;
import com.bank.sha.entity.User;
import com.bank.sha.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    private static final String IMAGE_DIRECTORY = "src/main/resources/static/images/";
    public String registerUser(RegisterUserDto registerUserDto) {
        try {
            // Check if user already exists
            User findUser = userRepository.findByEmail(registerUserDto.getEmail());
            if (findUser != null) {
                return "User already exists";
            }
            // Save KTP Image
            String ktpFileName = "ktp_" + registerUserDto.getEmail().toLowerCase() + ".jpg";
            saveBase64Image(registerUserDto.getKtp(), IMAGE_DIRECTORY + ktpFileName);

            // Save Profile Picture
            String ppFileName = "profile_picture_" + registerUserDto.getEmail().toLowerCase() + ".jpg";
            saveBase64Image(registerUserDto.getProfilePicture(), IMAGE_DIRECTORY + ppFileName);

            // Save User
            User user = new User();
            user.setName(registerUserDto.getName());
            user.setEmail(registerUserDto.getEmail().toLowerCase());
            user.setPassword(registerUserDto.getPassword());
            user.setUsername(registerUserDto.getUsername());
            user.setKtp(ktpFileName);
            user.setProfilePicture(ppFileName);
            userRepository.save(user);

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
