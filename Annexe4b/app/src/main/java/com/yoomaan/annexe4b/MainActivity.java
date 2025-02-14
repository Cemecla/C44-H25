package com.yoomaan.annexe4b;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    LinearLayout main;
    TextView setText;

    String input;
    String nip;



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

        main = findViewById(R.id.main);
        Ecouteur ec = new Ecouteur();
        input = "";
        setText = findViewById(R.id.output);
        nip = "57";

        System.out.println(main.getChildAt(1));

        for(int i = 0; i < main.getChildCount(); i++){

            if( main.getChildAt(i) instanceof LinearLayout) {
                LinearLayout mainChildren = (LinearLayout) main.getChildAt(i);

                for (int j = 0; j < mainChildren.getChildCount(); j++) {

                    if (mainChildren.getChildAt(j) instanceof Button) {
                        mainChildren.getChildAt(j).setOnClickListener(ec);
                    }

                }
            }
        }



    }

    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View source){
            //View est la superclasse de toutes les composantes

            Button button = (Button)source; // Transtype car on est certain que toutes nos sources sont des bouttons
            input+= button.getText().toString();

            setText.setText(input+"");
            String censured = "";
            for (int i = 0; i < input.length(); i++) censured += "*";
            setText.setText(censured);

            if(input.length()>=1)
                setText.setBackgroundColor(Color.WHITE);
            if(input.length() > 5 || input.equals(nip)){

                if(input.equals(nip)){
                    setText.setBackgroundColor(Color.GREEN);
                }
                else
                    setText.setBackgroundColor(Color.RED);
                //main.setBackgroundColor(getResources().getColor(R.color.vert,null)); -- version avec les variable personnalisé dans le rép. values

                input = "";
                setText.setText("");
            }


        }
    }
}