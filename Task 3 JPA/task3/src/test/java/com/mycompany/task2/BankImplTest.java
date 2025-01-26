package com.mycompany.task2;

import com.mycompany.task2.repository.AccountRepositoryImpl;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import com.mycompany.task2.model.Account;
import com.mycompany.task2.model.AccountOperation;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class BankImplTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private AccountRepositoryImpl repository;

    private void clearDatabase() {
        em.createQuery("DELETE FROM AccountOperation").executeUpdate();
        em.createQuery("DELETE FROM Account").executeUpdate();
    }

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("testPU");
        em = emf.createEntityManager();
        repository = new AccountRepositoryImpl();
        repository.setEm(em);

        em.getTransaction().begin();
        clearDatabase();
        seedDatabase();
        em.getTransaction().commit();
        em.clear();
    }
    private void seedDatabase() {

        Account account1 = new Account();
        account1.setName("John Doe");
        account1.setAddress("123 Main St");
        account1.setBalance(BigDecimal.ZERO);
        em.persist(account1);

        AccountOperation op1 = new AccountOperation();
        op1.setType(AccountOperation.Type.WIDTHRAW);
        op1.setAmount(new BigDecimal("50.00"));
        op1.setDate(Date.valueOf(LocalDate.of(2024, 1, 1)));
        op1.setAccount(account1);
        account1.addOperation(op1);
        em.persist(op1);

        AccountOperation op2 = new AccountOperation();
        op2.setType(AccountOperation.Type.DEPOSIT);
        op2.setAmount(new BigDecimal("100.00"));
        op2.setDate(Date.valueOf(LocalDate.of(2024, 6, 15)));
        op2.setAccount(account1);
        account1.addOperation(op2);
        em.persist(op2);

        Account account2 = new Account();
        account2.setName("Jane Smith");
        account2.setAddress("456 Elm St");
        account2.setBalance(BigDecimal.ZERO);
        em.persist(account2);

        AccountOperation op3 = new AccountOperation();
        op3.setType(AccountOperation.Type.WIDTHRAW);
        op3.setAmount(new BigDecimal("30.00"));
        op3.setDate(Date.valueOf(LocalDate.of(2023, 12, 31)));         op3.setAccount(account2);
        account2.addOperation(op3);
        em.persist(op3);

        Account account3 = new Account();
        account3.setName("Jane Smith");
        account3.setAddress("456 Elm St");
        account3.setBalance(BigDecimal.ZERO);
        em.persist(account3);

        em.flush();

    }

    @Test
    public void testFindDebitOperations() {
        em.getTransaction().begin();

        Long accountId = em.createQuery("SELECT a.id FROM Account a WHERE a.name = 'John Doe'", Long.class)
                .getSingleResult();
        assertNotNull(accountId, "Account ID should exist");

        List<AccountOperation> debitOperations = repository.findDebitOperations(accountId);

        assertEquals(1, debitOperations.size(), "Account 1 should have 1 debit operation");

        em.getTransaction().commit();
    }


    @Test
    public void testFindCreditOperations() {
        List<AccountOperation> credits = repository.findCreditOperations(1L);
        assertEquals(0, credits.size(), "Account 1 should have 0 credit operations");
    }
    @Test
    public void testComputeBalance() {
        em.getTransaction().begin();

        Long accountId = em.createQuery("SELECT a.id FROM Account a WHERE a.name = 'John Doe'", Long.class)
                .getSingleResult();
        assertNotNull(accountId, "Account ID should exist");

        BigDecimal balance = repository.computeBalance(accountId);

        assertEquals(new BigDecimal("50.00").setScale(2), balance.setScale(2), "Account balance should be 50.00 after withdrawal and deposit");

        em.getTransaction().commit();
    }
    @Test
    public void testFindAccountsWithNoOperations() {
        List<Account> accounts = repository.findAccountsWithNoOperations();
        assertEquals(1, accounts.size(), "There should be 1 account with no operations");
        assertEquals("Jane Smith", accounts.get(0).getName(), "Account with no operations should be Jane Smith");
    }

    @Test
    public void testFindAccountsWithMostOperations() {
        List<Account> accounts = repository.findAccountsWithMostOperations();
        assertEquals(1, accounts.size(), "There should be 1 account with the most operations");
        assertEquals("John Doe", accounts.get(0).getName(), "Account with most operations should be John Doe");
    }

    @Test
    public void testFindOperationsByType() {
        em.getTransaction().begin();

        Account accountTest = new Account();
        accountTest.setName("Test Account");
        accountTest.setAddress("789 Test St");
        accountTest.setBalance(BigDecimal.ZERO);
        em.persist(accountTest);

        AccountOperation op1 = new AccountOperation();
        op1.setType(AccountOperation.Type.WIDTHRAW);
        op1.setAmount(new BigDecimal("50.00"));
        op1.setDate(Date.valueOf(LocalDate.of(2024, 1, 1)));
        op1.setAccount(accountTest);
        accountTest.addOperation(op1);

        em.persist(op1);
        em.flush();

        em.getTransaction().commit();

        em.getTransaction().begin();
        List<AccountOperation> withdrawOps = repository.findOperationsByType(accountTest.getId(), AccountOperation.Type.WIDTHRAW);
        assertEquals(1, withdrawOps.size(), "Test Account should have 1 withdraw operation");
        assertEquals(new BigDecimal("50.00"), withdrawOps.get(0).getAmount(), "Withdraw operation amount should match");
        em.getTransaction().commit();
    }


    @Test
    public void testDirectJPQLQuery() {
        em.getTransaction().begin();

        String jpql = "SELECT op FROM AccountOperation op " +
                "WHERE op.account.id = 1 AND op.date BETWEEN :startDate AND :endDate";

        List<AccountOperation> results = em.createQuery(jpql, AccountOperation.class)
                .setParameter("startDate", Date.valueOf(LocalDate.of(2024, 1, 1)))
                .setParameter("endDate", Date.valueOf(LocalDate.of(2024, 6, 30)))
                .getResultList();

        em.getTransaction().commit();
    }
    @Test
    public void testFindOperationsByDateRange() {
        em.getTransaction().begin();

        Long accountId = em.createQuery("SELECT a.id FROM Account a WHERE a.name = 'John Doe'", Long.class).getSingleResult();

        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        List<AccountOperation> operations = repository.findOperationsByDateRange(accountId, startDate, endDate);


        assertEquals(2, operations.size(), "Account should have 2 operations in the date range");

        em.getTransaction().commit();
    }
}
