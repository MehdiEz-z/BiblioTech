package Controller;

import Model.Livre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LivreController {
    private Connection connection;
    public LivreController(Connection connection) {
        this.connection = connection;
    }

    public boolean ajouterLivre(Livre livre) {
        try {
            String insertQuery = "INSERT INTO livre (titre, auteur, isbn, quantite) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());
            preparedStatement.setInt(4, livre.getQuantite());

            int rowCount = preparedStatement.executeUpdate();

            preparedStatement.close();

            return rowCount > 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                // Code d'erreur "23000" correspondant à une violation de contrainte UNIQUE
                System.out.println("ISBN existe déjà dans la base de données.");
            } else {
                System.out.println("Erreur lors de l'ajout du livre : " + e.getMessage());
            }
            return false;
        }
    }
}
