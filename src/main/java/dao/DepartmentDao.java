package dao;


import models.Department;
import models.News;
import models.User;

import java.util.List;

public interface DepartmentDao {
    //create
    void add (Department department);

    //read
    List<Department> getAll();
    Department findById(int id);
    List<User> getUsers(int departmentId);
    List<News> getNews(int departmentId);

    //update
//    void update(int id, String departmentName, String description, int numberOfEmployees);

    //delete
    void deleteById(int id);
    void clearAll();
}



