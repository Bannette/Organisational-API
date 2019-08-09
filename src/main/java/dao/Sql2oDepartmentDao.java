package dao;

import models.News;
import models.User;
import models.Department;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;


public class Sql2oDepartmentDao implements DepartmentDao{
    private final Sql2o sql2o;
    public Sql2oDepartmentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Department department){
        String sql = "INSERT INTO departments (departmentname, description, numberofemployees) VALUES (:departmentName, :description, :numberOfEmployees);";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);
        } catch(Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> getAll(){
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM departments")
                    .executeAndFetch(Department.class);
        }
    }

    @Override
    public Department findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM departments WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Department.class);
        }
    }


    @Override
    public void deleteById(int id) {
        String sql = "DELETE from departments WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();

        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from departments";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public List<User> getUsers(int departmentId) {
        String sql = "SELECT * FROM users where departmentId=:departmentId";
        try(Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public List<News> getNews(int departmentId) {
        String sql = "SELECT * FROM news where departmentId=:departmentId";
        try(Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(News.class);
        }
    }
}
