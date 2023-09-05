import Connection.Connected;
import Controller.CopieLivreController;
import Controller.LivreController;
import Model.Livre;

import java.sql.Connection;
import java.util.Scanner;

public class bibliotech {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = Connected.getConnection();

        System.out.println("Ajout d'un nouveau livre à la bibliothèque.");
        System.out.print("Titre du livre : ");
        String titre = scanner.nextLine();
        System.out.print("Auteur du livre : ");
        String auteur = scanner.nextLine();
        System.out.print("Numéro ISBN : ");
        String isbn = scanner.nextLine();
        System.out.print("Quantité : ");
        int quantite = Integer.parseInt(scanner.nextLine());

        Livre livre = new Livre(titre, auteur, isbn, quantite);
        LivreController livreController = new LivreController(connection);

        int livreId = livreController.ajouterLivre(livre);
        if (livreId != -1) {
            System.out.println("Le livre a été ajouté avec succès à la bibliothèque.");

            // Ajoutez les copies à la table "copie"
            CopieLivreController copieLivreController = new CopieLivreController(connection);

            for (int i = 0; i < quantite; i++) {
                copieLivreController.ajouterCopie(livreId, "disponible");
            }

            System.out.println("Les copies ont été ajoutés avec succès.");
        }

    }
}