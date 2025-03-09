package com.bank.sha.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserDto {

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "PIN is required")
    @Pattern(regexp = "^[0-9]{4}$", message = "PIN must be 4 digits")
    private String pin;

    private String ktp;

    private String profilePicture;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getPin() {
        return pin;
    }

    public String getKtp() {
        return ktp;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}
