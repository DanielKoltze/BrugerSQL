package models;

public class Group {
    private int id;
    private String name;
    private String rights;

    public Group(int id, String name, String rights) {
        this.id = id;
        this.name = name;
        this.rights = rights;
    }
    public String toString() {
        return id + " " + name + " " + rights;
    }
}
