package com.bank.sha.seed;

import com.bank.sha.entity.User;
import com.bank.sha.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UsersSeedTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setUsername("johndoe");
        user.setPassword("password123");
        user.setVerified(true);
        user.setProfilePicture("profile.jpg");
        user.setKtp("1234567890");

        User savedUser = userRepository.save(user);
        System.out.println("Saved User: " + savedUser);
    }

}
