package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoTest extends BaseDaoTests {

    private static final User USER_1 = new User(10001L, "cody", "$2a$10$x5q4wXLSz67dsH408YtHeOvWCyZtpNCshVs0be6jsjDxyUWznsG/O", "");
    private static final User USER_2 = new User(10002L, "user", "$2a$10$4anLf1ROQqVMkponfYxPN.1lVS1lLAWAvpGFizQcgR4j6T//AcGRm", "");
    private static final User USER_3 = new User(10003L, "test", "$2a$10$9S4paYUlI1rZoFCtD6GdR.DeCkg82vqWV52EvPbOGFS9rGozDKeee", "");
    private static final User USER_4 = new User(10004L, "testing", "$2a$10$BLFWU8JJ5BaZy1PBD1tSnu6VQ7fr1cg3JIblFvyhhX/pc4ERCRtk2", "");

    private JdbcUserDao sut;

    @Before
    public void setup() {
        sut = new JdbcUserDao(new JdbcTemplate(dataSource));
    }

    @Test
    public void findAllReturnsCorrectUsers() {
        List<User> userList = sut.findAll();

        assertUserMatches(userList.get(0), USER_1);
        assertUserMatches(userList.get(1), USER_2);
        assertUserMatches(userList.get(2), USER_3);
        assertUserMatches(userList.get(3), USER_4);
    }

    @Test
    public void findByUsernameReturnsCorrectUser() {
        User cody = sut.findByUsername("cody");
        User user = sut.findByUsername("user");
        User test = sut.findByUsername("test");
        User testing = sut.findByUsername("testing");

        assertUserMatches(cody, USER_1);
        assertUserMatches(user, USER_2);
        assertUserMatches(test, USER_3);
        assertUserMatches(testing, USER_4);
    }

    @Test
    public void findByUsernameReturnsCorrectId() {
        int codyID = 10001;
        int userID = 10002;
        int testID = 10003;
        int testingID = 10004;

        int cody = sut.findIdByUsername("cody");
        int user = sut.findIdByUsername("user");
        int test = sut.findIdByUsername("test");
        int testing = sut.findIdByUsername("testing");

        Assert.assertEquals(codyID, cody);
        Assert.assertEquals(userID, user);
        Assert.assertEquals(testID, test);
        Assert.assertEquals(testingID, testing);
    }

    @Test
    public void createUserReturnsTrue() {
        boolean created1 = sut.create("testUser", "test1234");
        boolean created2 = sut.create("hello", "world");

        Assert.assertTrue(created1);
        Assert.assertTrue(created2);
    }

    @Test
    public void createUserReturnsFalseWhenUsernameExists() {
        boolean created1 = sut.create("cody", "blah");
        boolean created2 = sut.create("test", "blah");

        Assert.assertFalse(created1);
        Assert.assertFalse(created2);
    }

    @Test
    public void createdUserReturnsCorrectValues() {
        User testUser = new User(1001L, "testUser", "testing123", "");
        User testUser2 = new User(1002L, "testBlah", "testing123", "");

        sut.create("testUser", "testing123");
        sut.create("testBlah", "blah");

        User actualUser1 = sut.findByUsername("testUser");
        User actualUser2 = sut.findByUsername("testBlah");

        Assert.assertEquals(testUser.getId(), actualUser1.getId());
        Assert.assertEquals(testUser.getUsername(), actualUser1.getUsername());

        Assert.assertEquals(testUser2.getId(), actualUser2.getId());
        Assert.assertEquals(testUser2.getUsername(), actualUser2.getUsername());

        // no easy way to check if password is correct
    }

    private void assertUserMatches(User expected, User actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
    }
}
