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

            while (resultSet.next()) {
                int userId = resultSet.getInt("id_bruger");
                String name = resultSet.getString("brugernavn");
                String password = resultSet.getString("kodeord");
                System.out.println(userId + ". " + name + " " + password);

            }
        } catch (Exception e) {
            System.out.println("Kunne ikke hente brugere: " + e.getMessage());
        }
    }

    public void insertUser(String userName, String password, int gruppeId) {
        String query = "INSERT INTO brugere VALUES (null,?,?);";  //null er fordi den er autoincromented og ? er fordi vi sætter dem senere
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Indsatte ikke række");
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
            System.out.print("kunne ikke slette bruger: ");
            e.printStackTrace();

        }
    }

    public static User selectUser(int id) {
        User selectedUser = null;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM brugere WHERE id_bruger = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            // While loop en gang imellem??
            resultSet.next();
            int userId = resultSet.getInt("id_bruger");
            String username = resultSet.getString("brugernavn");
            String password = resultSet.getString("kodeord");
            selectedUser = new User(userId, username, password);


        } catch (Exception e) {
            System.out.print("Kunne ikke hente bruger: ");
            e.printStackTrace();
        }
        return selectedUser;
    }
    public void updateUser(int userId) {
        User selectedUser = selectUser(userId);
        System.out.println("Du har valgt at redigere: " + selectedUser);
        Scanner input = new Scanner(System.in);
        System.out.println("Indtast nyt navn: ");
        String newName = input.next();
        System.out.println("Vælg nyt password: ");
        String newPassword = input.next();

        String query = "UPDATE brugere SET brugernavn ='" + newName + "', kodeord='" + newPassword + "' " +
                "WHERE id_bruger= " + userId;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            System.out.println("You updated: " + selectedUser);
        }
        catch(Exception e) {
            System.out.println("Kunne ikke opdatere bruger");
        }

    }
}

