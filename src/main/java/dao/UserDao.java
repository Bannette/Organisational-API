package dao;

import models.User;
import models.Department;

import java.util.List;

public interface UserDao {
    //create
    void add(User user);


    //read
    List<User> getAll();
    User findById(int id);
    List<User> getAllUsersByDepartment(int departmentId);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}

