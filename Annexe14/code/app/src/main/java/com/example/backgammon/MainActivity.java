package com.example.backgammon;

import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    LinearLayout mainView;



    Ecouteur ec;

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
        mainView = findViewById(R.id.main);
        ec = new Ecouteur();

        for (int i = 0; i < mainView.getChildCount() ; i++) {
            if(mainView.getChildAt(i) instanceof LinearLayout){
                LinearLayout linear_child = (LinearLayout) mainView.getChildAt(i);
                linear_child.setOnDragListener(ec);
                if(linear_child.getChildAt(0) instanceof ImageView){
                    linear_child.getChildAt(0).setOnTouchListener(ec);
                }

            }
        }

    }

    public class Ecouteur implements View.OnDragListener, View.OnTouchListener {

        Drawable normal_state = getResources().getDrawable(R.drawable.background_triangles,null);
        Drawable selected_state = getResources().getDrawable(R.drawable.background_selected_triangle,null);

        View jeton = null;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackground(selected_state);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackground(normal_state);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackground(normal_state);
                    // jeton.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DROP:
                    //récupérer le jeton resté sur la colonne de départ
                    jeton = (View) event.getLocalState();
                    //aller chercher le conteneur d'origine du jeton
                    LinearLayout parent =  (LinearLayout) jeton.getParent();
                    //Retirer le jeton invisible de sa colonne origine
                    parent.removeView(jeton);
                    // la nouvelle colonne
                    LinearLayout nouvelle_colonne = (LinearLayout) v;
                    // ajouter le jeton à la nouvelle colonne
                    nouvelle_colonne.addView(jeton);
                    // le remettre visible
                    jeton.setVisibility(View.VISIBLE);


                    break;

            }

            return true;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            View.DragShadowBuilder builder = new View.DragShadowBuilder(v); // Crée une ombre du jeton
            v.startDragAndDrop(null,builder, v,0);
            v.setVisibility(View.INVISIBLE); // cacher le jeton, car on est en train de le déplacer


                    // data est une donnée sup
                    // ex donnée d'une carte à déplacer

            return true;
        }


    }


}