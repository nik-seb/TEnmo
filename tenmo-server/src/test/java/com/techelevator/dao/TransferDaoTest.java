package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class TransferDaoTest extends BaseDaoTests {

    private static final User USER_1 = new User(10001L, "cody", "$2a$10$x5q4wXLSz67dsH408YtHeOvWCyZtpNCshVs0be6jsjDxyUWznsG/O", "");
    private static final User USER_2 = new User(10002L, "user", "$2a$10$4anLf1ROQqVMkponfYxPN.1lVS1lLAWAvpGFizQcgR4j6T//AcGRm", "");
    private static final User USER_3 = new User(10003L, "test", "$2a$10$9S4paYUlI1rZoFCtD6GdR.DeCkg82vqWV52EvPbOGFS9rGozDKeee", "");
    private static final User USER_4 = new User(10004L, "testing", "$2a$10$BLFWU8JJ5BaZy1PBD1tSnu6VQ7fr1cg3JIblFvyhhX/pc4ERCRtk2", "");

    private static final Account ACCOUNT_1 = new Account(20001L, USER_1, BigDecimal.valueOf(790.75));
    private static final Account ACCOUNT_2 = new Account(20002L, USER_2, BigDecimal.valueOf(1109.25));
    private static final Account ACCOUNT_3 = new Account(20003L, USER_3, BigDecimal.valueOf(1100.00));
    private static final Account ACCOUNT_4 = new Account(20004L, USER_4, BigDecimal.valueOf(1000.00));

    private static final Transfer TRANSFER_1 = new Transfer(30001L, 2, 2, ACCOUNT_1, ACCOUNT_2, BigDecimal.valueOf(10));
    private static final Transfer TRANSFER_2 = new Transfer(30002L, 2, 2, ACCOUNT_1, ACCOUNT_2, BigDecimal.valueOf(0.25));
    private static final Transfer TRANSFER_3 = new Transfer(30003L, 1, 1, ACCOUNT_3, ACCOUNT_1, BigDecimal.valueOf(10));
    private static final Transfer TRANSFER_4 = new Transfer(30004L, 1, 2, ACCOUNT_2, ACCOUNT_1, BigDecimal.valueOf(1));
    private static final Transfer TRANSFER_5 = new Transfer(30005L, 1, 1, ACCOUNT_2, ACCOUNT_1, BigDecimal.valueOf(10000));
    private static final Transfer TRANSFER_6 = new Transfer(30006L, 1, 2, ACCOUNT_1, ACCOUNT_2, BigDecimal.valueOf(100));
    private static final Transfer TRANSFER_7 = new Transfer(30007L, 1, 1, ACCOUNT_3, ACCOUNT_1, BigDecimal.valueOf(10));
    private static final Transfer TRANSFER_8 = new Transfer(30008L, 2, 2, ACCOUNT_1, ACCOUNT_3, BigDecimal.valueOf(100));

    private JdbcTransferDao sut;

    @Before
    public void setup() {
        sut = new JdbcTransferDao(new JdbcTemplate(dataSource));
    }

    @Test
    public void listTransfersReturnsCorrectTransfers() {
        List<Transfer> transferList1 = sut.listTransfers(20001);
        List<Transfer> transferList2 = sut.listTransfers(20002);
        List<Transfer> transferList3 = sut.listTransfers(20003);

        assertTransferMatches(TRANSFER_1, transferList1.get(0));
        assertTransferMatches(TRANSFER_2, transferList1.get(1));
        assertTransferMatches(TRANSFER_3, transferList1.get(2));
        assertTransferMatches(TRANSFER_4, transferList1.get(3));
        assertTransferMatches(TRANSFER_5, transferList1.get(4));
        assertTransferMatches(TRANSFER_6, transferList1.get(5));
        assertTransferMatches(TRANSFER_7, transferList1.get(6));
        assertTransferMatches(TRANSFER_8, transferList1.get(7));

        assertTransferMatches(TRANSFER_1, transferList2.get(0));
        assertTransferMatches(TRANSFER_2, transferList2.get(1));
        assertTransferMatches(TRANSFER_4, transferList2.get(2));
        assertTransferMatches(TRANSFER_5, transferList2.get(3));
        assertTransferMatches(TRANSFER_6, transferList2.get(4));

        assertTransferMatches(TRANSFER_3, transferList3.get(0));
        assertTransferMatches(TRANSFER_7, transferList3.get(1));
        assertTransferMatches(TRANSFER_8, transferList3.get(2));
    }

    @Test
    public void getPendingTransfersReturnsOnlyPending() {
        List<Transfer> pendingTransfers1 = sut.getPendingTransfers(20001);
        List<Transfer> pendingTransfers2 = sut.getPendingTransfers(20002);
        List<Transfer> pendingTransfers3 = sut.getPendingTransfers(20003);

        Assert.assertEquals(0, pendingTransfers1.size());
        Assert.assertEquals(1, pendingTransfers2.size());
        Assert.assertEquals(2, pendingTransfers3.size());

        assertTransferMatches(TRANSFER_5, pendingTransfers2.get(0));

        assertTransferMatches(TRANSFER_3, pendingTransfers3.get(0));
        assertTransferMatches(TRANSFER_7, pendingTransfers3.get(1));
    }

    @Test
    public void getSentRequestsReturnsOnlySent() {
        List<Transfer> sentTransfers1 = sut.getSentRequests(20001);
        List<Transfer> sentTransfers2 = sut.getSentRequests(20002);
        List<Transfer> sentTransfers3 = sut.getSentRequests(20003);

        Assert.assertEquals(3, sentTransfers1.size());
        Assert.assertEquals(0, sentTransfers2.size());
        Assert.assertEquals(0, sentTransfers3.size());

        assertTransferMatches(TRANSFER_3, sentTransfers1.get(0));
        assertTransferMatches(TRANSFER_5, sentTransfers1.get(1));
        assertTransferMatches(TRANSFER_7, sentTransfers1.get(2));
    }

    @Test
    public void getTransferByIdReturnsCorrectTransfer() {
        Transfer transfer1 = sut.getTransferById(30001);
        Transfer transfer2 = sut.getTransferById(30002);
        Transfer transfer3 = sut.getTransferById(30003);
        Transfer transfer4 = sut.getTransferById(30004);
        Transfer transfer5 = sut.getTransferById(30005);
        Transfer transfer6 = sut.getTransferById(30006);
        Transfer transfer7 = sut.getTransferById(30007);
        Transfer transfer8 = sut.getTransferById(30008);

        assertTransferMatches(TRANSFER_1, transfer1);
        assertTransferMatches(TRANSFER_2, transfer2);
        assertTransferMatches(TRANSFER_3, transfer3);
        assertTransferMatches(TRANSFER_4, transfer4);
        assertTransferMatches(TRANSFER_5, transfer5);
        assertTransferMatches(TRANSFER_6, transfer6);
        assertTransferMatches(TRANSFER_7, transfer7);
        assertTransferMatches(TRANSFER_8, transfer8);
    }

    @Test
    public void createdTransferReturnsTransferWithNewId() {
        Transfer newTransfer1 = new Transfer(-1, 1, 1, ACCOUNT_1, ACCOUNT_2, BigDecimal.valueOf(10));
        Transfer newTransfer2 = new Transfer(-1, 1, 1, ACCOUNT_1, ACCOUNT_2, BigDecimal.valueOf(100));

        Transfer createdTransfer1 = sut.createTransfer(newTransfer1);
        Transfer createdTransfer2 = sut.createTransfer(newTransfer2);

        newTransfer1.setTransferId(createdTransfer1.getTransferId());
        newTransfer2.setTransferId(createdTransfer2.getTransferId());

        assertTransferMatches(newTransfer1, createdTransfer1);
        assertTransferMatches(newTransfer2, createdTransfer2);
    }

    @Test
    public void updateTransferApprovalCorrectlyUpdatesTransfer() {
        Transfer updatedTransfer1 = new Transfer(30001L, 2, 3, ACCOUNT_1, ACCOUNT_2, BigDecimal.valueOf(10));
        Transfer updatedTransfer2 = new Transfer(30003L, 1, 2, ACCOUNT_3, ACCOUNT_1, BigDecimal.valueOf(10));

        Transfer actualUpdate1 = sut.updateTransferApproval(updatedTransfer1, 30001);
        Transfer actualUpdate2 = sut.updateTransferApproval(updatedTransfer2, 30003);

        Transfer testUpdate1 = sut.getTransferById(30001);
        Transfer testUpdate2 = sut.getTransferById(30003);

        assertTransferMatches(actualUpdate1, testUpdate1);
        assertTransferMatches(actualUpdate2, testUpdate2);
    }

    public void assertTransferMatches(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getTransferStatusId(), actual.getTransferStatusId());
        Assert.assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
        Assert.assertEquals(0, expected.getAmount().compareTo(actual.getAmount()));

        Account expectedAccountFrom = expected.getAccountFrom();
        Account actualAccountFrom = actual.getAccountFrom();

        Account expectedAccountTo = expected.getAccountTo();
        Account actualAccountTo = expected.getAccountTo();

        assertAccountMatches(expectedAccountFrom, actualAccountFrom);
        assertAccountMatches(expectedAccountTo, actualAccountTo);
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
