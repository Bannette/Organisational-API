package dao;

import models.News;
import java.util.List;

public interface NewsDao {
    //create
    void add(News news);


    //read
    List<News> getAll();
    News findById(int id);
    List<News> getAllNewsByDepartment(int departmentId);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}

