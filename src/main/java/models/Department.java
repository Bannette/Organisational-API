package models;

import java.util.Objects;

public class Department {
    private int id;
    private String departmentName;
    private String description;
    private int numberOfEmployees;

    public Department(String departmentName, String description, int numberOfEmployees) {
        this.departmentName = departmentName;
        this.description = description;
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return numberOfEmployees == that.numberOfEmployees &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentName, description, numberOfEmployees);
    }
}
