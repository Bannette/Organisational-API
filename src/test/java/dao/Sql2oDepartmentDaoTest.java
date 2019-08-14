package dao;

import models.Department;
import models.News;
import models.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {
    private static Connection conn;
    private static Sql2oDepartmentDao departmentDao;
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
    public void addingDepartmentSetsId() throws Exception {
        Department testDepartment = setupNewDepartment();
        int originalDepartmentId = testDepartment.getId();
        departmentDao.add(testDepartment);
        assertNotEquals(originalDepartmentId, testDepartment.getId());
    }

    @Test
    public void getAll() throws Exception{
        Department testDepartment = new Department("accounting", "handles firm budget", 6);
        departmentDao.add(testDepartment);
        assertEquals(1, departmentDao.getAll().size());
    }

    @Test
    public void addedDepartmentsAreReturnedFromGetAll() throws Exception {
        Department testDepartment = setupNewDepartment();
        assertEquals(1, departmentDao.getAll().size());
    }
    @Test
    public void noDepartmentsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectDepartment() throws Exception {
        Department testDepartment = setupNewDepartment();
        Department otherDepartment = setupNewDepartment();
        assertEquals(testDepartment, departmentDao.findById(testDepartment.getId()));
    }


    @Test
    public void deleteByIdDeletesCorrectDepartment() throws Exception {
        Department testDepartment = setupNewDepartment();
        departmentDao.deleteById(testDepartment.getId());
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Department testDepartment = setupNewDepartment();
        Department otherDepartment = setupNewDepartment();
        departmentDao.clearAll();
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void getAllUsersForADepartmentReturnsUsersCorrectly() {
        Department testDepartment = new Department ("accounting", "handles company's finance", 5);
        departmentDao.add(testDepartment);
        int theId = testDepartment.getId();
        User firstUser = new User("Apiyo", theId, "accountant");
        userDao.add(firstUser);
        User secondUser = new User("Annette", theId, "programmer");
        userDao.add(secondUser);

        User[] users = {firstUser, secondUser};
        assertEquals(Arrays.asList(users), departmentDao.getUsers(testDepartment.getId()));
    }

    @Test
    public void getAllNewsForADepartmentReturnsUsersCorrectly() {
        Department testDepartment = new Department ("accounting", "handles company's finance", 5);
        departmentDao.add(testDepartment);
        int theId = testDepartment.getId();
        News firstNews = new News("Bob dies", theId);
        newsDao.add(firstNews);
        News secondNews = new News("Michael Joseph appointed interim CEO", theId);
        newsDao.add(secondNews);

        News[] news = {firstNews, secondNews};
        assertEquals(Arrays.asList(news), departmentDao.getNews(testDepartment.getId()));
    }
    public Department setupNewDepartment(){
        Department department = new Department("accounting", "handles company's finance", 5);
        departmentDao.add(department);
        return department;
    }
}
