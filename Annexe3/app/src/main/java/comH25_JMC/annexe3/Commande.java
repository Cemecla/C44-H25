package comH25_JMC.annexe3;

import java.util.Vector;

public class Commande {

    private Vector<Produit> listeCommande;

    public Commande ( )
    {
        listeCommande = new Vector();
    }

    public void ajouterProduit ( Produit p )
    {
        listeCommande.add(p);
    }

    public double total ()
    {
	double total =0;
      // compléter : total de la commande

        for(Produit p : listeCommande)
            total += p.getPrix();
        return total;



    }

    public double taxes()
    {
        double taxes = 0;
        double total = total();
	// compléter : montant des taxes sur le total de la commande

        // tps sur le montant avant taxes ( 5% )
        taxes += total *0.05;
        //tvq sur le montant avant taxes ( 9.975% )
        taxes += total * 0.09975
        // taxes total = tps + tvq

	

        return taxes;
    }

    public double grandTotal(){
	
	double grTotal = 0;
	
	// compléter


	return grTotal;
	

    }
}
