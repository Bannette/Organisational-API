package models;

public class News {
    private String content;
    private String writtenBy;
    private int rating;
    private int id;
    private int restaurantId; //will be used to connect Restaurant to Review (one-to-many)

    public News(String content, String writtenBy, int rating, int restaurantId) {
        this.content = content;
        this.writtenBy = writtenBy;
        this.rating = rating;
        this.restaurantId = restaurantId;
    }
}


