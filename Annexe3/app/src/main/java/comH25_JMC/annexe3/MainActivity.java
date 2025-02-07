package comH25_JMC.annexe3;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView litresConsomme;
    ProgressBar barre;
    ImageButton Bidon;
    ImageButton Bouteille;
    ImageButton Verre;

    int qtemili;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        litresConsomme = findViewById(R.id.qteTotal);
        barre = findViewById(R.id.progressBar);

        //Boutons
        Bidon = findViewById(R.id.BidonBouton);
        Bouteille = findViewById(R.id.BouteilleBouton);
        Verre = findViewById(R.id.VerreBouton);

        //
        qtemili = 0;


        // Étape 1
        Ecouteur ec = new Ecouteur();
        Bidon.setOnClickListener(ec);
        Bouteille.setOnClickListener(ec);
        Verre.setOnClickListener(ec);

        barre.setProgress(0);
    }

    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View source) {

            if(qtemili < 2000) {
                if (source == Bidon && (qtemili + 1000 <= 2000))
                    qtemili += 1000;
                else
                if (source == Bouteille&& (qtemili + 500 <= 2000))
                    qtemili += 500;
                else
                if (source == Verre&& (qtemili + 250 <= 2000))
                    qtemili += 250;


                barre.setProgress(qtemili);
                litresConsomme.setText(qtemili +" ml");
            }
        }
    }
}