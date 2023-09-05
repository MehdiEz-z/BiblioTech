package Controller;

import Model.Livre;

import java.sql.*;

public class LivreController {
    private Connection connection;
    public LivreController(Connection connection) {
        this.connection = connection;
    }

    public int ajouterLivre(Livre livre) {
        try {
            String insertQuery = "INSERT INTO Livre (titre, auteur, isbn, quantite) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());
            preparedStatement.setInt(4, livre.getQuantite());

            int rowCount = preparedStatement.executeUpdate();

            // Récupérez l'ID généré après l'insertion
            if (rowCount > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // ID du livre inséré
                }
            }

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du livre : " + e.getMessage());
        }

        return -1;
    }

}
