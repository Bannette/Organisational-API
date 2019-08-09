package dao;


import models.Department;
import models.News;
import models.User;

import java.util.List;

public interface DepartmentDao {

    void add (Department department);


    List<Department> getAll();
    Department findById(int id);
    List<User> getUsers(int departmentId);
    List<News> getNews(int departmentId);


void update(int id, String departmentName, String description, int numberOfEmployees);


    void deleteById(int id);
    void clearAll();
}



