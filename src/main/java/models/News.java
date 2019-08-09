package models;

import java.util.Objects;

public class News {
    private int id;
    private String content;
    private String description;
    private int departmentId;


    public News(String content) {
        this.content = content;
        this.description = "general news";
    }
    public News(String content, int departmentId) {
        this.content = content;
        this.departmentId = departmentId;
        this.description = "department news";
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return Objects.equals(content, news.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}




