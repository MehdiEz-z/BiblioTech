package Model;

public class Livre {
    private String titre;
    private String auteur;
    private String isbn;
    private String status;
    private int quantite;

    // Constructeur
    public Livre(String titre, String auteur, String isbn, String status, int quantite) {
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.status = status;
        this.quantite = quantite;
    }

    // Getters et setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

