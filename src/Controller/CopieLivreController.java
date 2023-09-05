package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CopieLivreController {
    private Connection connection;
    public CopieLivreController(Connection connection) {
        this.connection = connection;
    }

    public boolean ajouterCopie(int livreId, String statut) {
        try {
            String insertQuery = "INSERT INTO copie (livre_id, statut) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, livreId);
            preparedStatement.setString(2, statut);

            int rowCount = preparedStatement.executeUpdate();

            preparedStatement.close();

            return rowCount > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout des copies : " + e.getMessage());
            return false;
        }
    }
}
