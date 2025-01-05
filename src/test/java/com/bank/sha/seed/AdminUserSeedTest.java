package com.bank.sha.seed;

import com.bank.sha.entity.AdminUser;
import com.bank.sha.repository.AdminUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminUserSeedTest {

    @Autowired
    private AdminUserRepository adminUserRepository;


    @Test
    public void testAddAdminUser() {
        AdminUser adminUser = new AdminUser();
        adminUser.setName("John Admin");
        adminUser.setEmail("john.admin@example.com");
        adminUser.setPassword("password123");
        adminUserRepository.save(adminUser);

        System.out.println("Saved Admin User: " + adminUser);
    }
}
