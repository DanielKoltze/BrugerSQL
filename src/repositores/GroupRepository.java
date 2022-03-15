package repositores;

import java.sql.*;

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
}

