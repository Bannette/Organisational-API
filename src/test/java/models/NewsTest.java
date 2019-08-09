package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void newsInstantiatesCorrectly_true() {
        News newNews = setupNews();
        assertEquals(true, newNews instanceof News);
    }

    @Test
    public void getContentGetsContentCorrectly_String() {
        News newNews = setupNews();
        assertEquals("Ceo, Bob Collimore, dies", newNews.getContent());
    }

    @Test
    public void setContentSetsContentCorrectly() {
        News newNews = setupNews();
        newNews.setContent("Michael Joseph appointed new interim CEO");
        assertEquals("Michael Joseph appointed new interim CEO", newNews.getContent());
    }

    @Test
    public void newsReturnsTrueIfContentIsSame() {
        News newNews = setupNews();
        News anotherNews = setupNews();
        assertEquals( true, newNews.equals(anotherNews));
    }

    @Test
    public void getTypeGetsTypeCorrectly() {
        News newNews = new News("Ceo, Bob Collimore, dies", 4 );
        assertEquals(4, newNews.getDepartmentId());
    }

    @Test
    public void setDepartmentIdSetsDepartmentId() {
        News newNews = setupNews();
        newNews.setDepartmentId(8);
        assertEquals(8, newNews.getDepartmentId());
    }

    public News setupNews(){
        return new News("Ceo, Bob Collimore, dies");
    }
}
