package repositores;

import models.Group;

import java.sql.*;
import java.util.Scanner;

public class GroupRepository {
    private static String DB_URL = "jdbc:mysql://localhost:3306/bruger?useSSL=false&serverTimezone=UTC"; //efter3306 skriver hvad det er for en tabel
    private static String user = "root";
    private static String password = "masp123123";
    private static Connection connection;



    public void showAllGroups() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM gruppe";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int groupId = resultSet.getInt("gruppe_id");
                String name = resultSet.getString("gruppe_navn");
                String rights = resultSet.getString("gruppe_rettigheder");
                System.out.println(groupId + ". " + name + " " + rights);
            }
        }
        catch (Exception e) {
            System.out.println("Kunne ikke hente grupper: " + e.getMessage());
        }
    }
    public void updateGroup(int id) {
        Group selectedGroup = selectGroup(id);

        System.out.println("Du har valgt at redigere: " + selectedGroup);
        Scanner input = new Scanner(System.in);
        System.out.print("Indtast nyt gruppenavn: ");
        String newGroupName = input.nextLine();
        System.out.print("Indtast nye grupperettigheder: ");
        String newGroupRights = input.nextLine();
        String query = "UPDATE gruppe SET gruppe_navn = ?, gruppe_rettigheder = ? " +
                "WHERE gruppe_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newGroupName);
            preparedStatement.setString(2, newGroupRights);
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
            Group updatedGroup = new Group(id, newGroupName, newGroupRights);
            System.out.println("You updated: " + selectedGroup + "to " + updatedGroup);

        }
        catch (Exception e) {
            System.out.println("Kunne ikke opdatere gruppe: " + e.getMessage());
        }

    }
    public Group selectGroup(int id) {
        Group selectedGroup = null;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM gruppe WHERE gruppe_id = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int groupId = resultSet.getInt("gruppe_id");
                String groupName = resultSet.getString("gruppe_navn");
                String groupRights = resultSet.getString("gruppe_rettigheder");
                selectedGroup = new Group(groupId, groupName, groupRights);
            }
        }
        catch (Exception e) {
            System.out.println("Kunne ikke hente gruppe: " + e.getMessage());
        }
        return selectedGroup;
    }


    public void insertGroup(String name, String rights) {
        String query = "INSERT INTO gruppe VALUES (null,?,?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, rights);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Indsatte ikke række: " + e.getMessage());
        }
    }
    public void connectToMySQL() {
        try {
            connection = DriverManager.getConnection(DB_URL, user, password);
            System.out.println("Virker nu");
        } catch (Exception e) {
            System.out.println("Virker ikke");
        }
    }

    public void deleteGroup(int groupID) {
        Group selectedGroup = selectGroup(groupID);
        String query = "DELETE FROM gruppe WHERE gruppe_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, groupID);
            preparedStatement.executeUpdate();
            System.out.println("Du har slettet gruppe: " + selectedGroup);
        }
        catch (Exception e) {
            System.out.println("Kunne ikke slette gruppe: " + e.getMessage());
        }
    }
}

