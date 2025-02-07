package comH25_JMC.annexe3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Vector;

public class VoyageVoyagePlusLoinQueLaNuitEtLeJourVoyage extends AppCompatActivity {


    TextView nbrAvion;
    TextView nbrHotel;
    TextView Total ;

    ImageView btnAvion;
    ImageView btnHotel;
    Button btnvalidate;

    Commande commande;
    int qteAvion;
    int qteHotel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voyage_voyage_plus_loin_que_la_nuit_et_le_jour_voyage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Valeurs






        // outputs
         nbrAvion = findViewById(R.id.avionTotal);
         nbrHotel = findViewById(R.id.HotelTotal);
         Total = findViewById(R.id.returnTotal);

         //Boutons
         btnAvion = findViewById(R.id.imageAvion);
         btnHotel = findViewById(R.id.imageHotel);
         btnvalidate = findViewById(R.id.validation);

         Ecouteur ec = new Ecouteur();

         btnAvion.setOnClickListener(ec);
         btnHotel.setOnClickListener(ec);
         btnvalidate.setOnClickListener(ec);

         commande = new Commande();


    }
    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View source) {
            if(source == btnAvion){
                BilletAvion p = new BilletAvion();
                commande.ajouterProduit(p);
            }
            else {
                HebergementHotel p = new HebergementHotel();
                commande.ajouterProduit(p);
            }



            nbrAvion.setText(qteAvion);
            nbrHotel.setText(qteHotel);
        }
    }
}