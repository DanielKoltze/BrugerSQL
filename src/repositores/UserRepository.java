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

        Menu menu = new Menu("Options til brugere i", "Vælg en af overstående", new String[]{"1. Opret", "2. Update", "3. Slet", "4. Hent", "5. luk program"});
        boolean isRunning = true;
        connectToMySQL();
        int userId;
        Scanner input = new Scanner(System.in);
        while (isRunning) {
            menu.printMenu();
            switch (menu.readChoice()) {
                case 1:
                    System.out.println("Skriv brugernavnet:");
                    String name = input.next();
                    System.out.println("Skriv kodeordet:");
                    String password = input.next();
                    System.out.println("Skriv gruppe id: 1=guest  2=student");
                    insertData(name, password, gruppeId);
                    System.out.println("Du har tilføjet " + name + " " + password);
                    break;
                case 2:
                    System.out.println("Skriv id på den bruger du gerne vil opdatere:");
                    userId = input.nextInt();
                    updateUser(userId);
                    break;
                case 3:
                    System.out.println("Skriv ID på den bruger du vil slette");
                    userId = input.nextInt();
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
            String sql = "SELECT * FROM brugere INNER JOIN gruppe ON gruppe.gruppe_id = brugere.gruppe_id";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int userId = resultSet.getInt("id_bruger");
                String brugernavn = resultSet.getString("brugernavn");
                String password = resultSet.getString("kodeord");
                String name = resultSet.getString("name");
                String rettigheder = resultSet.getString("rettigheder");
                System.out.println(userId + ". " + brugernavn + " " + password + "         " + name + "  " + rettigheder);

            }
        } catch (Exception e) {
            System.out.println("Viser ikke alle rows");
        }
    }

    static void insertData(String userName, String password, int gruppeId) {
        String query = "INSERT INTO brugere VALUES (null,?,?,?);";  //null er fordi den er autoincromented og ? er fordi vi sætter dem senere
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, gruppeId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Indsatte ikke række");
        }
    }

    static void connectToMySQL() {
        try {
            connection = DriverManager.getConnection(DB_URL, user, password);
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

    public static void updateUser(int userId){
        Scanner input = new Scanner(System.in);
        User selectedUser = selectUser(userId);
        System.out.println("Du er igang med at opdatere " + selectedUser);
        System.out.println("Indtast det nye brugernavn:");
        String newBrugernavn = input.next();
        System.out.println("Indtast det nye kodeord:");
        String newKodeord = input.next();
        String query = "UPDATE brugere SET brugernavn = '" + newBrugernavn + "', kodeord= '" + newKodeord + "' WHERE id_bruger = " + userId;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            System.out.println("You updated: " + selectedUser + " to " + userId + " " + newBrugernavn + " " + newKodeord);

        } catch (Exception e) {
            System.out.print("kunne ikke slette bruger: ");
            e.printStackTrace();

        }

    }


}

