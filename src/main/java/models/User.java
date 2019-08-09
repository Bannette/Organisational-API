package models;

import java.util.Objects;

public class User {
    private int id;
    private String userName;
    private int departmentId;
    private String role;

    public User(String userName, int departmentId, String role) {
        this.userName = userName;
        this.departmentId = departmentId;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return departmentId == user.departmentId &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, departmentId, role);
    }
}


