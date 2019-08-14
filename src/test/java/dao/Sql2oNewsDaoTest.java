package dao;

import models.Department;
import models.News;
import models.User;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oNewsDaoTest {
    private static Connection conn;
    private static Sql2oDepartmentDao departmentDao;
    private  static Sql2oNewsDao newsDao;
    private  static Sql2oUserDao userDao;
    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/apis_test";
        Sql2o sql2o = new Sql2o(connectionString, "user", "666Nikol-");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
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
    public void addingNewsSetsId() throws Exception{
        News testNews = setupNews();
        int originalNewsId = testNews.getId();
        newsDao.add(testNews);
        assertNotEquals(originalNewsId, testNews.getId());;
    }

    @Test
    public void addedNewsAreReturnedFromGetAll() throws Exception {
        News testNews = new News("Ceo, Bob Collimore, dies");
        newsDao.add(testNews);
        assertEquals(1, newsDao.getAll().size());
    }

    @Test
    public void noNewsReturnsEmptyList() throws Exception {
        assertEquals(0, newsDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectNews() throws Exception {
        News news = new News("Ceo, Bob Collimore, dies");
        newsDao.add(news);
        newsDao.deleteById(news.getId());
        assertEquals(0, newsDao.getAll().size());
    }


    @Test
    public void clearAll() throws Exception {
        News testNews = setupNews();
        News otherNews = setupNews();
        newsDao.clearAll();
        assertEquals(0, newsDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectNews() throws Exception {
        News testNews = setupNews();
        News otherNews = setupNews();
        assertEquals(testNews, newsDao.findById(testNews.getId()));
    }

    @Test
    public void add_addDepartmentIdIntoDB_true() {
        Department testDepartment = new Department("accounting", "handles firm accounting", 6);
        departmentDao.add(testDepartment);
        News testNews = new News("Bob dies", testDepartment.getId());
        newsDao.add(testNews);
        assertEquals(testNews.getDepartmentId(), testDepartment.getId());
    }
    @Test
    public void getAllNewsByDepartment() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment();
        News news1 = setupNewsForDepartment(testDepartment);
        News news2 = setupNewsForDepartment(testDepartment);
        News newsForOtherDepartment = setupNewsForDepartment(otherDepartment);
        assertEquals(2, newsDao.getAllNewsByDepartment(testDepartment.getId()).size());
    }

    public News setupNewsForDepartment(Department department) {
        News news = new News("Michael joseph appointed interim CEO", department.getId());
        newsDao.add(news);
        return news;
    }

    public Department setupDepartment() {
        Department department = new Department("accounting", "handles firm budget", 6);
        departmentDao.add(department);
        return department;
    }

    public News setupNews(){
        News news = new News("Ceo, Bob Collimore, dies");
        newsDao.add(news);
        return news;
    }
}
