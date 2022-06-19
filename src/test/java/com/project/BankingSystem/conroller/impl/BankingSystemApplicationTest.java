package com.project.BankingSystem.conroller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.BankingSystem.classes.Address;
import com.project.BankingSystem.classes.Money;
import com.project.BankingSystem.controller.dto.CheckingDTO;
import com.project.BankingSystem.controller.dto.QuantityDTO;
import com.project.BankingSystem.controller.dto.TransferThirdPartyDTO;
import com.project.BankingSystem.models.*;
import com.project.BankingSystem.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;



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

//      Trying different ways to persist the data.
        Optional<User> user = userRepository.findById(1L);
        role.setUser(user.get());
        roleRepository.save(role);
        Address address = new Address("Cll Hort", "Horta", "08032");
        Address address1 = new Address("Av del Vien to", "Terrassa", "08005");
        AccountHolder accountHolder = new AccountHolder("jimeca",
                passwordEncoder.encode("ironhacker"),
                role1, "Charo Mendez", new Date(93, 7, 14), address);
        AccountHolder accountHolder1 = new AccountHolder("anna",
                passwordEncoder.encode("iepa"),
                role1, "Anna Caro O", new Date(95, 8, 7), address1);
        accountHolderRepository.save(accountHolder);
        accountHolderRepository.save(accountHolder1);
        user = userRepository.findById(2L);
        role1.setUser(user.get());
        roleRepository.save(role1);
        roleRepository.save(new Role("USER", accountHolder1));

        ThirdParty thirdParty = new ThirdParty("paypal_tp", passwordEncoder.encode("paypal"),
                role2, "PayPal");
        thirdPartyRepository.save(thirdParty);
        user = userRepository.findById(4L);
        role2.setUser(user.get());
        roleRepository.save(role2);


//  =================== Accounts ===================
        Checking checking = new Checking(new Money(new BigDecimal(873.45)), accountHolder);
        Checking checking1 = new Checking(new Money(new BigDecimal(1004.82)), accountHolder1);
        List<Account> accountList = new ArrayList<>();
        accountList.add(checking);
        accountList.add(checking1);
        checking.setSecretKey(345123);
        checking.setId(231423534677234L);
        checking1.setId(231423534677534L);
        checkingRepository.save(checking);
        checkingRepository.save(checking1);
        accountHolder.setAccounts(accountList);
        accountHolderRepository.save(accountHolder);
        accountHolderRepository.save(accountHolder1);
        AccountHolder accountHolder2 = new AccountHolder("tester", passwordEncoder.encode("testing"),
                role1, "Tester Tests", new Date(120, 2, 3), address1);
        accountHolderRepository.save(accountHolder2);
        roleRepository.save(new Role("USER", accountHolder2));
        StudentChecking studentChecking = new StudentChecking(new Money(new BigDecimal(358.12)), accountHolder2);
        accountList.add(studentChecking);
        studentCheckingRepository.save(studentChecking);
        Savings savings = new Savings(new Money(new BigDecimal(1304.76)), accountHolder2);
        accountList.add(savings);
        savingsRepository.save(savings);
        CreditCard creditCard = new CreditCard(new Money(new BigDecimal(139.00)), accountHolder2);
        accountList.add(creditCard);
        creditCard.setId(435657823123612345L);
        creditCardRepository.save(creditCard);
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
        accountHolderRepository.deleteAll();
        adminRepository.deleteAll();
        thirdPartyRepository.deleteAll();
        userRepository.deleteAll();
        studentCheckingRepository.deleteAll();
        checkingRepository.deleteAll();
        savingsRepository.deleteAll();
        creditCardRepository.deleteAll();
        accountRepository.deleteAll();
    }



    @Test
    void accessAccount_ValidSecretKey_AccountInfo() throws Exception{
        MvcResult result = mockMvc.perform(get("/access-account/345123")).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("jimeca"));
    }

    @Test
    void accessAccountAdmin_ValidId_AccountInfo() throws Exception {
        MvcResult result = mockMvc.perform(get("/access-account/admin/231423534677234")).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("jimeca"));
    }

    @Test
    void createAccountHolder_ValidInfo_Created() throws Exception{
        Address address = new Address("Cll Hort", "Horta", "08032");
        AccountHolder accountHolder = new AccountHolder("tester", "test",
                new Role("USER"), "Tester Tests", new Date(120, 2, 3), address);
        String body = objectMapper.writeValueAsString(accountHolder);
        MvcResult result = mockMvc.perform(post("/create/account-holder")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Horta"));

    }
    @Test
    void getAccountBalance_ValidSecretKey_Balance() throws Exception{
        MvcResult result = mockMvc.perform(get("/get/account-balance/345123")).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("873.45"));
    }
    @Test
    void accounts_NoParams_ListOfAccounts() throws Exception {
        MvcResult result = mockMvc.perform(get("/show-accounts")).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("100.00"));
    }

    @Test
    void transferMoney_AccountsIds_MoneyTransferred() throws Exception {
        QuantityDTO quantityDTO = new QuantityDTO(new BigDecimal(250.00));
        String body = objectMapper.writeValueAsString(quantityDTO);
        MvcResult result = mockMvc.perform(patch("/transfer/231423534677234/231423534677534")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();
        assertEquals(623.45, checkingRepository.findById(231423534677234L).get().getBalance().getAmount().doubleValue());
    }

    @Test
    void createChecking_Valid_CheckingCreated() throws Exception{
        CheckingDTO checkingDTO = new CheckingDTO(new BigDecimal(322.67), 2L);
        String body = objectMapper.writeValueAsString(checkingDTO);
        MvcResult result = mockMvc.perform(post("/create/checking")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("322.67"));
    }

    @Test
    void creditAccountBalance_Id_BalanceIncreased() throws Exception{
        QuantityDTO quantityDTO = new QuantityDTO(new BigDecimal(250.00));
        String body = objectMapper.writeValueAsString(quantityDTO);
        MvcResult result = mockMvc.perform(patch("/credit/account/231423534677234")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();
        assertEquals(1123.45, checkingRepository.findById(231423534677234L).get().getBalance().getAmount().doubleValue());

    }

    @Test
    void creditAccountTP_Valid_BalanceIncreased() throws Exception {
        TransferThirdPartyDTO transferThirdPartyDTO = new TransferThirdPartyDTO(new BigDecimal(250.00),
                231423534677234L);
        String body = objectMapper.writeValueAsString(transferThirdPartyDTO);
        MvcResult result = mockMvc.perform(patch("/creditTP/account/-1911368973")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();
        assertEquals(1123.45, checkingRepository.findById(231423534677234L).get().getBalance().getAmount()
                .doubleValue());
    }

}