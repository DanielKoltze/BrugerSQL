package repositores;

import models.User;

import java.sql.*;
import java.util.Scanner;


public class UserRepository {
    //kommentar

    private static String DB_URL = "jdbc:mysql://localhost:3306/bruger?useSSL=false&serverTimezone=UTC"; //efter3306 skriver hvad det er for en tabel
    private static String user = "root";
    private static String password = "masp123123";
    private static Connection connection;


    public void showAllUsers() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM brugere";
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Liste med brugere: ");

            while (resultSet.next()) {
                int userId = resultSet.getInt("id_bruger");
                String name = resultSet.getString("brugernavn");
                String password = resultSet.getString("kodeord");
                int groupId = resultSet.getInt("gruppe_id");
                System.out.println(userId + ". " + name + " " + password + " " + groupId);

            }
        } catch (Exception e) {
            System.out.println("Kunne ikke hente brugere: " + e.getMessage());
        }
    }

    public void insertUser(String userName, String password, int gruppeId) {
        String query = "INSERT INTO brugere VALUES (null,?,?,?);";  //null er fordi den er autoincromented og ? er fordi vi sætter dem senere
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, gruppeId);
            preparedStatement.executeUpdate();

            // Bruger ikke User's toString, da der ikke er givet noget ID endnu
            System.out.println("Du oprettede: " + userName + " " + password);
        } catch (Exception e) {
            System.out.println("Indsatte ikke user: " + e.getMessage());
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

    public void deleteUser(int userId) {
        User selectedUser = selectUser(userId);
        String query = "DELETE FROM brugere WHERE id_bruger=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            System.out.println("You deleted: " + selectedUser);

        } catch (Exception e) {
            System.out.print("kunne ikke slette bruger: " + e.getMessage());


        }
    }

    public static User selectUser(int id) {
        User selectedUser = null;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM brugere WHERE id_bruger = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int userId = resultSet.getInt("id_bruger");
                String username = resultSet.getString("brugernavn");
                String password = resultSet.getString("kodeord");
                int groupId = resultSet.getInt("gruppe_id");
                selectedUser = new User(userId, username, password, groupId);
            }


        } catch (Exception e) {
            System.out.print("Kunne ikke hente bruger: " + e.getMessage());
            e.printStackTrace();
        }
        return selectedUser;
    }
    public void updateUser(int userId) {
        User selectedUser = selectUser(userId);

        System.out.println("Du har valgt at redigere: " + selectedUser);
        Scanner input = new Scanner(System.in);
        System.out.println("Indtast nyt navn: ");
        String newName = input.nextLine();
        System.out.println("Vælg nyt password: ");
        String newPassword = input.nextLine();
        System.out.println("Vælg nyt gruppe id");
        int newGroupID = Integer.parseInt(input.nextLine());

        String query = "UPDATE brugere SET brugernavn = ?, kodeord = ?, gruppe_id = ? " +
                "WHERE id_bruger = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newPassword);
            preparedStatement.setInt(3, newGroupID);
            preparedStatement.setInt(4, userId);

            preparedStatement.executeUpdate();
            User updatedUser = new User(userId, newName, newPassword, newGroupID);
            System.out.println("You updated: " + selectedUser + "to " + updatedUser);
        }
        catch(Exception e) {
            System.out.println("Kunne ikke opdatere bruger: " + e.getMessage());
        }

    }
}

