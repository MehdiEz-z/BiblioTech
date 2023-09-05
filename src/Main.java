import  java.sql.*;
import java.util.Scanner;
import Connection.Connected;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = Connected.getConnection()) {
            String sqlQuery = "SELECT * FROM livre";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Traitement du résultat ici
            while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println(); // Nouvelle ligne après chaque ligne
            }
            Connected.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}