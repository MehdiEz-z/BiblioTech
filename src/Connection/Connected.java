package Connection;

import java.sql.*;

public final class Connected {
    private static final String DB_NAME = "bibliotech";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String SERVER_NAME = "localhost";
    private static Connection con = null;

    private Connected() {
    }

    public static Connection getConnection() {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://" + SERVER_NAME + "/" + DB_NAME, USERNAME, PASSWORD);
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Erreur de connection avec la base de donnée: " + e);
            }
        }

        return con;
    }

    public static void disconnect() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Erreur de déconnexion: " + e.getMessage());
        }

        con = null;
    }
}
