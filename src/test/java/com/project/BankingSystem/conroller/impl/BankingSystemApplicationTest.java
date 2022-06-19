package com.project.BankingSystem.conroller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.BankingSystem.models.Admin;
import com.project.BankingSystem.models.Role;
import com.project.BankingSystem.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class BankingSystemApplicationTests {

    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Role role = new Role("ADMIN");
        Role role1 = new Role("USER");
        Role role2 = new Role("THIRD_PARTY");
        roleRepository.save(role);
        roleRepository.save(role1);
        roleRepository.save(role2);
        adminRepository.save(
                new Admin(
                        "angie",
                        passwordEncoder.encode("admin"),
                        role,
                        "Angie Jimenez"
                )
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void contextLoads() {
    }

}