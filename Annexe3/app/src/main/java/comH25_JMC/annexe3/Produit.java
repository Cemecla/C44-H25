package comH25_JMC.annexe3;
public class Produit {
    private String nom;
    private double prix;

    public Produit(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }


    public double getPrix() {
        return prix;
    }
}
