package dao;

import models.Department;
import models.User;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oUserDaoTest {
    private static  Connection conn;
    private  static  Sql2oDepartmentDao departmentDao;
    private static Sql2oUserDao userDao;
    private static Sql2oNewsDao newsDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/apis_test";
        Sql2o sql2o = new Sql2o(connectionString, "user", "666Nikol-");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        departmentDao.clearAll();
        userDao.clearAll();
        newsDao.clearAll();
    }
    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingUserSetsId() throws Exception {
        User testUser = setupNewUser();
        int originalUserId = testUser.getId();
        userDao.add(testUser);
        assertNotEquals(originalUserId, testUser.getId());
    }

    @Test
    public void addedUsersAreReturnedFromGetAll() throws Exception {
        User testUser = setupNewUser();
        userDao.add(testUser);
        assertEquals(1, userDao.getAll().size());
    }

    @Test
    public void noUserReturnsEmptyList() throws Exception {
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectUser() throws Exception {
        User user = setupNewUser();
        userDao.add(user);
        userDao.deleteById(user.getId());
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        User testUser = setupNewUser();
        User otherUser = setupNewUser();
        userDao.clearAll();
        assertEquals(0, userDao.getAll().size());
    }
    @Test
    public void add_addDepartmentIdIntoDB_true() {
        Department testDepartment = new Department("accounting", "handles firm accounting", 6);
        departmentDao.add(testDepartment);
        User testUser = new User("Bubbles", testDepartment.getId(), "auditor");
        userDao.add(testUser);
        assertEquals(testUser.getDepartmentId(), testDepartment.getId());
    }

    @Test
    public void getAllUsersByDepartment() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment();
        User user1 = setupUserForDepartment(testDepartment);
        User user2 = setupUserForDepartment(testDepartment);
        User userForOtherDepartment = setupUserForDepartment(otherDepartment);
        assertEquals(2, userDao.getAllUsersByDepartment(testDepartment.getId()).size());
    }

    public Department setupDepartment() {
        Department department = new Department("accounting", "handles firm budget", 6);
        departmentDao.add(department);
        return department;
    }

    public User setupUserForDepartment(Department department) {
        User user = new User("Annette", department.getId(), "programmer");
        userDao.add(user);
        return user;
    }
    public User setupNewUser() {
        return new User("Apiyo", 1, "accountant");
    }
}
