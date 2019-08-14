package dao;

import models.News;
import java.util.List;
public interface NewsDao {

    void add(News news);



    List<News> getAll();
    News findById(int id);
    List<News> getAllNewsByDepartment(int departmentId);


    void deleteById(int id);
    void clearAll();
}

