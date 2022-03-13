package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class AccountDaoTest extends BaseDaoTests {

    private static final User USER_1 = new User(10001L, "cody", "$2a$10$x5q4wXLSz67dsH408YtHeOvWCyZtpNCshVs0be6jsjDxyUWznsG/O", "");
    private static final User USER_2 = new User(10002L, "user", "$2a$10$4anLf1ROQqVMkponfYxPN.1lVS1lLAWAvpGFizQcgR4j6T//AcGRm", "");
    private static final User USER_3 = new User(10003L, "test", "$2a$10$9S4paYUlI1rZoFCtD6GdR.DeCkg82vqWV52EvPbOGFS9rGozDKeee", "");
    private static final User USER_4 = new User(10004L, "testing", "$2a$10$BLFWU8JJ5BaZy1PBD1tSnu6VQ7fr1cg3JIblFvyhhX/pc4ERCRtk2", "");

    private static final Account ACCOUNT_1 = new Account(20001L, USER_1, BigDecimal.valueOf(790.75));
    private static final Account ACCOUNT_2 = new Account(20002L, USER_2, BigDecimal.valueOf(1109.25));
    private static final Account ACCOUNT_3 = new Account(20003L, USER_3, BigDecimal.valueOf(1100.00));
    private static final Account ACCOUNT_4 = new Account(20004L, USER_4, BigDecimal.valueOf(1000.00));

    private JdbcAccountDao sut;

    @Before
    public void setup() {
        sut = new JdbcAccountDao(new JdbcTemplate(dataSource));
    }

    @Test
    public void getUserBalanceReturnsCorrectValue() {
        BigDecimal accountOneBalance = sut.getUserBalance(10001L);
        BigDecimal accountTwoBalance = sut.getUserBalance(10002L);
        BigDecimal accountThreeBalance = sut.getUserBalance(10003L);
        BigDecimal accountFourBalance = sut.getUserBalance(10004L);

        Assert.assertEquals(0, ACCOUNT_1.getBalance().compareTo(accountOneBalance));
        Assert.assertEquals(0, ACCOUNT_2.getBalance().compareTo(accountTwoBalance));
        Assert.assertEquals(0, ACCOUNT_3.getBalance().compareTo(accountThreeBalance));
        Assert.assertEquals(0, ACCOUNT_4.getBalance().compareTo(accountFourBalance));
    }

    @Test
    public void updateBalanceCorrectlyUpdatesBalance() {
        Account newAccount1 = new Account(20001L, USER_1, BigDecimal.valueOf(1000.00));
        Account newAccount2 = new Account(20002L, USER_2, BigDecimal.valueOf(2500.00));

        sut.updateBalance(newAccount1);
        sut.updateBalance(newAccount2);

        BigDecimal newBalance1 = sut.getUserBalance(10001L);
        BigDecimal newBalance2 = sut.getUserBalance(10002L);

        Assert.assertEquals(0, newAccount1.getBalance().compareTo(newBalance1));
        Assert.assertEquals(0, newAccount2.getBalance().compareTo(newBalance2));
    }

    @Test
    public void getAccountByIdReturnsCorrectAccount() {
        Account account1 = sut.getAccountById(10001L);
        Account account2 = sut.getAccountById(10002L);
        Account account3 = sut.getAccountById(10003L);
        Account account4 = sut.getAccountById(10004L);

        assertAccountMatches(ACCOUNT_1, account1);
        assertAccountMatches(ACCOUNT_2, account2);
        assertAccountMatches(ACCOUNT_3, account3);
        assertAccountMatches(ACCOUNT_4, account4);
    }

    @Test
    public void getAllAccountsReturnsCorrectAccounts() {
        List<Account> accountList = sut.getAllAccounts();

        assertAccountMatches(ACCOUNT_1, accountList.get(0));
        assertAccountMatches(ACCOUNT_2, accountList.get(1));
        assertAccountMatches(ACCOUNT_3, accountList.get(2));
        assertAccountMatches(ACCOUNT_4, accountList.get(3));
    }

    public void assertAccountMatches(Account expected, Account actual) {
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(0, expected.getBalance().compareTo(actual.getBalance()));

        User expectedUser = expected.getUser();
        User actualUser = expected.getUser();

        assertUserMatches(expectedUser, actualUser);
    }

    public void assertUserMatches(User expected, User actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
    }

}
