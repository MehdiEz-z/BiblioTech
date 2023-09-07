package Controller;

import Model.Livre;

import java.sql.*;

public class LivreController {
    private Connection connection;
    public LivreController(Connection connection) {
        this.connection = connection;
    }

    public Livre ajouterLivre(Livre livre) {

        Livre livreAjouter = null;
        try {
            String insertQuery = "INSERT INTO Livre (titre, auteur, isbn, quantite) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());
            preparedStatement.setInt(4, livre.getQuantite());

            int rowCount = preparedStatement.executeUpdate();

            if (rowCount > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1); // Récupérez l'ID généré
                    livreAjouter = livre;
                    livreAjouter.setId(id);
                }
            }

            preparedStatement.close();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                // Code d'erreur "23000" correspondant à une violation de contrainte UNIQUE
                System.out.println("Cet ISBN existe déjà.");
            } else {
                System.out.println("Erreur lors de l'ajout du livre : " + e.getMessage());
            }
        }
        return livreAjouter;
    }

}
