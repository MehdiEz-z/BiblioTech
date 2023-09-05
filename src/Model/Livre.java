package Model;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String isbn;
    private int quantite;

    // Constructeur
    public Livre(String titre, String auteur, String isbn, int quantite) {
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.quantite = quantite;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

