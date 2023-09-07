import Connection.Connected;
import Controller.CopieLivreController;
import Controller.LivreController;
import Model.Livre;

import java.sql.Connection;
import java.util.Scanner;

public class Bibliotech {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = Connected.getConnection();

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Afficher les livres");
            System.out.println("3. Chercher un livre");
            System.out.println("4. Emprunter un livre");
            System.out.println("5. Statistiques");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Pour consommer la ligne vide

            switch (choix) {
                case 1:
                    ajouterLivre(connection, scanner);
                    break;
                case 2:
                    afficherLivres(connection, scanner);
                    break;
                case 3:
                    chercherLivre(connection, scanner);
                    break;
                case 4:
                    emprunterLivre(connection, scanner);
                    break;
                case 5:
                    afficherStatistiques(connection);
                    break;
                case 0:
                    System.out.println("Merci d'avoir utilisé BiblioTech. Au revoir !");
                    System.exit(0);
                default:
                    System.out.println("Option invalide. Veuillez choisir une option valide.");
                    break;
            }
        }
    }

    private static void ajouterLivre(Connection connection, Scanner scanner) {
        while (true) {
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

            Livre livreAjoute = livreController.ajouterLivre(livre);
            if (livreAjoute != null) {
                System.out.println("Le livre a été ajouté avec succès à la bibliothèque.");

                // Ajoutez les copies à la table "copie"
                CopieLivreController copieLivreController = new CopieLivreController(connection);

                for (int i = 0; i < quantite; i++) {
                    copieLivreController.ajouterCopie(livreAjoute.getId(), "Disponible");
                }

                System.out.println("Les copies ont été ajoutées avec succès.");

                // Demande à l'utilisateur s'il veut réajouter un livre, revenir au menu ou quitter

                System.out.println("Que souhaitez-vous faire ?");
                System.out.println("1. Réajouter un livre");
                System.out.println("2. Revenir au menu");
                System.out.println("0. Quitter");
                System.out.print("Choix : ");
                int choix = Integer.parseInt(scanner.nextLine());

                switch (choix) {
                    case 1:
                        // Réajouter un livre
                        break;
                    case 2:
                        // Revenir au menu principal
                        return;
                    case 0:
                        // Quitter le programme
                        System.out.println("Merci d'avoir utilisé BiblioTech. Au revoir !");
                        System.exit(0);
                    default:
                        System.out.println("Option invalide. Revenez au menu principal.");
                }
            }
        }
    }



    private static void afficherLivres(Connection connection, Scanner scanner) {
        LivreController livreController = new LivreController(connection);
        livreController.afficherLivres();

        System.out.println("Que souhaitez-vous faire ?");
        System.out.println("1. Revenir au menu");
        System.out.println("0. Quitter");
        System.out.print("Choix : ");
        int choix = Integer.parseInt(scanner.nextLine());

        switch (choix) {
            case 1:
                // Revenir au menu principal
                return;
            case 0:
                // Quitter le programme
                System.out.println("Merci d'avoir utilisé BiblioTech. Au revoir !");
                System.exit(0);
            default:
                System.out.println("Option invalide. Revenez au menu principal.");
        }
    }

    private static void chercherLivre(Connection connection, Scanner scanner) {
        // Implémentez la recherche de livre ici
    }

    private static void emprunterLivre(Connection connection, Scanner scanner) {
        // Implémentez l'emprunt de livre ici
    }

    private static void afficherStatistiques(Connection connection) {
        // Implémentez l'affichage des statistiques ici
    }
}