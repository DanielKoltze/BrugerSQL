package repositores;

import models.User;

import java.sql.*;
import java.util.Scanner;


public class UserRepository {
    //kommentar

    private static String DB_URL = "jdbc:mysql://localhost:3306/bruger?useSSL=false&serverTimezone=UTC"; //efter3306 skriver hvad det er for en tabel
    private static String user = "root";
    private static String password = "rootroot";
    private static Connection connection;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Menu menu = new Menu("Options til brugere i", "Vælg en af overstående", new String[]{"1. Opret", "2. Update", "3. Slet", "4. Hent", "5. luk program"});
        boolean isRunning = true;
        connectToMySQL();
        while (isRunning) {
            menu.printMenu();
            switch (menu.readChoice()) {
                case 1:
                    System.out.println("Skriv brugernavnet:");
                    String name = input.next();
                    System.out.println("Skriv kodeordet:");
                    String password = input.next();
                    insertData(name, password);
                    System.out.println("Du har tilføjet " + name + " " + password);
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("Skriv ID på den bruger du vil slette");
                    int userId = input.nextInt();
                    deleteUser(userId);
                    break;
                case 4:
                    showAllRows();
                    System.out.println();
                    System.out.println();
                    break;
                case 5:
                    isRunning = false;
                    break;
            }
        }
    }

    static void showAllRows() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM brugere";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int userId = resultSet.getInt("id_bruger");
                String name = resultSet.getString("brugernavn");
                String password = resultSet.getString("kodeord");
                System.out.println(userId + ". " + name + " " + password);

            }
        } catch (Exception e) {
            System.out.println("Viser ikke alle rows");
        }
    }

    static void insertData(String userName, String password) {
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

    static void connectToMySQL() {
        try {
            connection = DriverManager.getConnection(DB_URL, user, password);
            System.out.println("Virker nu");
        } catch (Exception e) {
            System.out.println("Virker ikke");
        }
    }

    static void deleteUser(int userId) {
        User selectedUser = selectUser(userId);
        String query = "DELETE FROM brugere WHERE id_bruger = " + userId;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
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


}

