import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUserDao;
import models.Department;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import exceptions.ApiException;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        Sql2oDepartmentDao departmentDao;
        Sql2oNewsDao newsDao;
        Sql2oUserDao userDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/apis";
        Sql2o sql2o = new Sql2o(connectionString, "user", "666Nikol-");

        departmentDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();
        //CREATE

        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            res.type("application/json");
            return gson.toJson(department);
        });

        post("/departments/:departmentId/news/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            News news = gson.fromJson(req.body(), News.class);
            news.setDepartmentId(departmentId);
            newsDao.add(news);
            res.status(201);
            res.type("application/json");
            return gson.toJson(news);
        });

        post("/departments/:departmentId/users/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            User users = gson.fromJson(req.body(), User.class);
            users.setDepartmentId(departmentId);
            userDao.add(users);
            res.status(201);
            res.type("application/json");
            return gson.toJson(users);
        });

        post("/users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            res.status(201);
            res.type("application/json");
            return gson.toJson(user);
        });

        post("/news/new", "application/json", (req, res) -> {
            res.type("application/json");
            News news = gson.fromJson(req.body(), News.class);
            newsDao.add(news);
            res.status(201);
            res.type("application/json");
            return gson.toJson(news);
        });

        //Read


        get("/departments", "application/json", (req, res) -> {
            res.type("application/json");
            System.out.println(departmentDao.getAll());

            if(departmentDao.getAll().size() > 0){
                return gson.toJson(departmentDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }

        });

        get("/departments/:id", "application/json", (req, res) -> {

            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            res.type("application/json");
            return gson.toJson(departmentToFind);
        });

        get("/departments/:id/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Department departmentToFind = departmentDao.findById(departmentId);
            List<News> allNews;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            allNews = newsDao.getAllNewsByDepartment(departmentId);
            res.type("application/json");
            return gson.toJson(allNews);
        });
        get("/departments/:id/users", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Department departmentToFind = departmentDao.findById(departmentId);
            List<User> allUsers;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            allUsers = userDao. getAllUsersByDepartment(departmentId);
            res.type("application/json");
            return gson.toJson(allUsers);
        });
        get("/news", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(newsDao.getAll());
        });

        get("/users", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(userDao.getAll());

        });
        get("/departments/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "department-form.hbs");
        },new HandlebarsTemplateEngine());

        post("/departments", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String departmentName = request.queryParams("departmentName");
            String description = request.queryParams("description");
            int numberOfEmployees = Integer.parseInt(request.queryParams("numberOfEmployees"));
            Department newDepartment = new Department(departmentName, description, numberOfEmployees);
            departmentDao.add(newDepartment);
            model.put("departments", departmentDao.getAll());
            return new ModelAndView(model, "department.hbs");
        }, new HandlebarsTemplateEngine());
        get("/departments", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "department.hbs");
        },new HandlebarsTemplateEngine());
        get("/users", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "user.hbs");
        },new HandlebarsTemplateEngine());
        post("/users", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String userName = request.queryParams("userName");
            int departmentId = Integer.parseInt(request.queryParams("departmentId"));
            String role = request.queryParams("role");
            User newUser = new User(userName, departmentId, role);
            userDao.add(newUser);
            model.put("users", userDao.getAll());
            return new ModelAndView(model, "user.hbs");
        }, new HandlebarsTemplateEngine());
        get("/news", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "news-form.hbs");
        },new HandlebarsTemplateEngine());
        post("/news", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String content = request.queryParams("content");
            int departmentId = Integer.parseInt(request.queryParams("departmentId"));
            News newNews = new News(content, departmentId);
            newsDao.add(newNews);
            model.put("news", newsDao.getAll());
            return new ModelAndView(model, "news.hbs");
        }, new HandlebarsTemplateEngine());
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "index.hbs");
        },new HandlebarsTemplateEngine());

    }
}