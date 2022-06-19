package com.project.BankingSystem;

import com.project.BankingSystem.classes.Address;
import com.project.BankingSystem.classes.Money;
import com.project.BankingSystem.models.*;
import com.project.BankingSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

    @Component
    public class Data implements CommandLineRunner {
        @Autowired
        private AccountHolderRepository accountHolderRepository;
        @Autowired
        private AccountRepository accountRepository;
        @Autowired
        private CheckingRepository checkingRepository;
        @Autowired
        private SavingsRepository savingsRepository;
        @Autowired
        private StudentCheckingRepository studentCheckingRepository;
        @Autowired
        private CreditCardRepository creditCardRepository;
        @Autowired
        private AdminRepository adminRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private RoleRepository roleRepository;
        @Autowired
        private ThirdPartyRepository thirdPartyRepository;


        //  This method has been created merely to add some sample data to the DB and, hence, to have the opportunity to test
//  the http requests.
        public void sampleData() {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Role role = new Role("ADMIN");
            Role role1 = new Role("USER");
            Role role2 = new Role("THIRD_PARTY");
            roleRepository.save(role);
            roleRepository.save(role1);
            roleRepository.save(role2);
//  =================== Admin ===================
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
//  =================== AccountHolder ===================
            AccountHolder accountHolder = new AccountHolder("jimeca",
                    passwordEncoder.encode("ironhacker"),
                    role1, "Charo Reina", new Date(120, 1, 14), address);
            AccountHolder accountHolder1 = new AccountHolder("anna",
                    passwordEncoder.encode("iepa"),
                    role1, "Anna Caro O", new Date(122, 12, 21), address1);
            accountHolderRepository.save(accountHolder);
            accountHolderRepository.save(accountHolder1);
            user = userRepository.findById(2L);
            role1.setUser(user.get());
            roleRepository.save(role1);
            roleRepository.save(new Role("USER", accountHolder1));
//  =================== ThirdParty ===================
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
            checkingRepository.save(checking);
            checkingRepository.save(checking1);
            accountHolder.setAccounts(accountList);
            accountHolderRepository.save(accountHolder);
            accountHolderRepository.save(accountHolder1);
            AccountHolder accountHolder2 = new AccountHolder("tester", passwordEncoder.encode("testing"),

                    role1, "Tester Tests", new Date(100, 1, 12), address1);
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
            creditCardRepository.save(creditCard);

        }


        @Override
        public void run(String... args) throws Exception {
            sampleData();
        }
    }

