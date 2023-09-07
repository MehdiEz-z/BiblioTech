package Controller;

import Model.Livre;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

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

    public void afficherLivres() {
        try {
            String query = "SELECT titre, auteur, " +
                    "SUM(CASE WHEN statut = 'disponible' THEN 1 ELSE 0 END) AS quantite_disponible, " +
                    "SUM(CASE WHEN statut = 'emprunté' THEN 1 ELSE 0 END) AS quantite_emprunte, " +
                    "SUM(CASE WHEN statut = 'perdu' THEN 1 ELSE 0 END) AS quantite_perdu " +
                    "FROM livre " +
                    "LEFT JOIN copie ON livre.id = copie.livre_id " +
                    "GROUP BY titre, auteur";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Statistiques des livres :");
            System.out.println("-------------------------------------------------------------");
            System.out.printf("%-20s %-20s %-5s %-5s %-5s%n",
                    "Titre", "Auteur", "Quantité Disponible", "Quantité Empruntée", "Quantité Perdue");
            System.out.println("-------------------------------------------------------------");

            while (resultSet.next()) {
                String titre = resultSet.getString("titre");
                String auteur = resultSet.getString("auteur");
                int quantiteDisponible = resultSet.getInt("quantite_disponible");
                int quantiteEmprunte = resultSet.getInt("quantite_emprunte");
                int quantitePerdu = resultSet.getInt("quantite_perdu");

                System.out.printf("%-20s %-20s %-5d %-5d %-5d%n",
                        titre, auteur, quantiteDisponible, quantiteEmprunte, quantitePerdu);
            }

            System.out.println("-------------------------------------------------------------");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des statistiques : " + e.getMessage());
        }
    }

    public List<Livre> rechercherLivres(String recherche) {
        List<Livre> resultats = new ArrayList<>();

        try {
            String query = "SELECT l.titre, l.auteur, l.isbn, l.quantite, " +
                    "COUNT(c.id) AS quantite_copies_dispo " +
                    "FROM livre AS l " +
                    "LEFT JOIN copie AS c ON l.id = c.livre_id " +
                    "WHERE l.titre LIKE ? OR l.auteur LIKE ? " +
                    "AND (c.statut IS NULL OR c.statut = ?) " +
                    "GROUP BY l.id, l.titre, l.auteur, l.isbn, l.quantite";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + recherche + "%"); // Recherche dans le titre
            preparedStatement.setString(2, "%" + recherche + "%"); // Recherche dans l'auteur
            preparedStatement.setBoolean(3, true); // Copies disponibles

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String titre = resultSet.getString("titre");
                String auteur = resultSet.getString("auteur");
                String isbn = resultSet.getString("isbn");
                int quantite = resultSet.getInt("quantite");
                int quantiteCopiesDispo = resultSet.getInt("quantite_copies_dispo");

                Livre livre = new Livre(titre, auteur, isbn, quantite);
                livre.setQuantiteCopiesDispo(quantiteCopiesDispo);
                resultats.add(livre);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de livres : " + e.getMessage());
        }

        return resultats;
    }

}
