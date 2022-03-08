package models;

public class User {
    private int userId;
    private String username;
    private String password;

    public User (int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
    public String toString() {
        return userId + " " + username + " " + password;
    }

}

