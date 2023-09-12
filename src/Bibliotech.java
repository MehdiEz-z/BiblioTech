import Connection.Connected;
import Controller.CopieLivreController;
import Controller.LivreController;
import Model.Livre;

import java.sql.Connection;
import java.util.Scanner;
import java.util.List;

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
        while (true) {
            System.out.println("Choisissez un type de recherche :");
            System.out.println("1. Rechercher par titre ou auteur");
            System.out.println("2. Rechercher par ISBN");
            System.out.println("0. Revenir au menu principal");
            System.out.print("Choix : ");

            int choixRecherche = Integer.parseInt(scanner.nextLine());

            switch (choixRecherche) {
                case 1:
                    rechercherParTitreAuteur(connection, scanner);
                    break;
                case 2:
                    rechercherParISBN(connection, scanner);
                    break;
                case 0:
                    // Revenir au menu principal
                    return;
                default:
                    System.out.println("Option invalide. Veuillez choisir une option valide.");
            }
        }
    }

    private static void rechercherParTitreAuteur(Connection connection, Scanner scanner) {
        System.out.print("Entrer le titre ou le nom d'auteur : ");
        String recherche = scanner.nextLine();

        LivreController livreController = new LivreController(connection);
        List<Livre> resultats = livreController.rechercherLivres(recherche);

        if (resultats.isEmpty()) {
            System.out.println("Aucun livre trouvé pour la recherche : " + recherche);
        } else {
            System.out.println("Livres trouvés pour la recherche :");
            for (Livre livre : resultats) {
                System.out.println("Titre : " + livre.getTitre());
                System.out.println("Auteur : " + livre.getAuteur());
                System.out.println("ISBN : " + livre.getIsbn());
                System.out.println("Quantité : " + livre.getQuantite());
                System.out.println("Copies disponibles : " + livre.getQuantiteCopiesDispo());
                System.out.println();
            }
        }

        System.out.println("Que souhaitez-vous faire ?");
        System.out.println("1. Nouvelle recherche");
        System.out.println("2. Revenir au menu principal");
        System.out.println("0. Quitter");
        System.out.print("Choix : ");
        int choix = Integer.parseInt(scanner.nextLine());

        switch (choix) {
            case 1:
                // Nouvelle recherche (continue la boucle)
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

    private static void rechercherParISBN(Connection connection, Scanner scanner) {
        System.out.print("Entrer ISBN : ");
        String isbn = scanner.nextLine();

        LivreController livreController = new LivreController(connection);
        Livre livre = livreController.getLivreByISBN(isbn);

        if (livre != null) {
            System.out.println("Livre trouvé :");
            System.out.println("Titre : " + livre.getTitre());
            System.out.println("Auteur : " + livre.getAuteur());
            System.out.println("ISBN : " + livre.getIsbn());

            System.out.println("Que souhaitez-vous faire ?");
            System.out.println("1. Modifier ce livre");
            System.out.println("2. Supprimer ce livre");
            System.out.println("3. Emprunter ce livre");
            System.out.println("4. Retourner ce livre");
            System.out.println("5. Nouvelle recherche");
            System.out.println("6. Revenir au menu principal");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");
            int choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {
                case 1:
                    System.out.print("Nouveau titre : ");
                    String nouveauTitre = scanner.nextLine();
                    System.out.print("Nouvel auteur : ");
                    String nouvelAuteur = scanner.nextLine();

                    boolean modificationReussie = livreController.modifierLivreParISBN(isbn, nouveauTitre, nouvelAuteur);

                    if (modificationReussie) {
                        System.out.println("Les informations du livre avec l'ISBN " + isbn + " ont été mises à jour avec succès.");
                    } else {
                        System.out.println("Échec de la mise à jour des informations du livre avec l'ISBN : " + isbn + ".");
                    }
                    break;
                case 2:
                    // Supprimer le livre
                    System.out.print("Voulez-vous vraiment supprimer ce livre ? (O/N) : ");
                    String confirmation = scanner.nextLine();

                    if (confirmation.equalsIgnoreCase("O")) {
                        boolean suppressionReussie = livreController.supprimerLivreParISBN(isbn);

                        if (suppressionReussie) {
                            System.out.println("Le livre avec l'ISBN " + isbn + " a été supprimé avec succès.");
                        } else {
                            System.out.println("Échec de la suppression du livre avec l'ISBN " + isbn + ".");
                        }
                    } else {
                        System.out.println("Suppression annulée.");
                    }

                    break;
                case 0:
                    // Quitter le programme
                    System.out.println("Merci d'avoir utilisé BiblioTech. Au revoir !");
                    System.exit(0);
                default:
                    System.out.println("Option invalide. Revenez au menu principal.");
            }
        } else {
            System.out.println("Aucun livre trouvé avec l'ISBN : " + isbn);
            System.out.println("Que souhaitez-vous faire ?");
            System.out.println("1. Nouvelle recherche");
            System.out.println("2. Revenir au menu principal");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");
            int choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {
                case 1:
                    // Nouvelle recherche (continue la boucle)
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


    private static void emprunterLivre(Connection connection, Scanner scanner) {
    }

    private static void afficherStatistiques(Connection connection) {
    }
}