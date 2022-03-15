package models;

public class User {
    private int userId;
    private String username;
    private String password;
    private int groupId;

    public User (int userId, String username, String password, int groupId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.groupId = groupId;
    }

    public User(String username, String password, int groupId) {
        this.username = username;
        this.password = password;
        this.groupId = groupId;
    }

    public String toString() {
        return userId + " " + username + " " + password + " " + groupId;
    }

}

