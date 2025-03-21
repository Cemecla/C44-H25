package com.CaronCorp.tp1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout zone_dessin;

    int largeurTrait;
    LinearLayout mainView;

    // Permet de mettre le int de l'id des Frames Images
    int seleced_Tool;
    int selected_Color;

    boolean isStroke;


    //Variable pour la gestion des dessins
    Point depart;
    Point pendant;
    Point arrive;
    Point active;

    SurfaceDessin surf;

    Vector<Forme> dessins;
    Path tempPath;

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
        Ecouteur ec = new Ecouteur();
        dessins = new Vector<>();
//        btn_LargeurTrait = findViewById(R.id.taille_trait);
//        btn_Crayon = findViewById(R.id.crayon);
        largeurTrait = 1;
        zone_dessin = findViewById(R.id.zone_dessin);
        seleced_Tool = -1;

        mainView = findViewById(R.id.main);
        surf = new SurfaceDessin(this);
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1));
        zone_dessin.addView(surf);

        //

        //btn_LargeurTrait.setOnClickListener(ec);
        //btn_Crayon.setOnClickListener(ec);


        zone_dessin.setOnTouchListener(ec);

        for (int i = 0; i < mainView.getChildCount() ; i++) {

            if(mainView.getChildAt(i) instanceof HorizontalScrollView){
                HorizontalScrollView ScrollFrame =  (HorizontalScrollView) mainView.getChildAt(i);

                if(ScrollFrame.getChildAt(0) instanceof LinearLayout){
                    LinearLayout tempFrame = (LinearLayout) ScrollFrame.getChildAt(0);
                    for (int j = 0; j< tempFrame.getChildCount();j++){
                        tempFrame.getChildAt(j).setOnClickListener(ec);
                    }
                }
            }
        }

    }
    public int getLargeurTrait() {
        return largeurTrait;
    }

    public void setLargeurTrait(int largeurTrait) {
        this.largeurTrait = largeurTrait;
    }




    private class Ecouteur implements View.OnClickListener, View.OnTouchListener {



        @Override
        public void onClick(View source) {
            // on peut faire apparaitre une boite de dialogue



            if(source == findViewById(R.id.taille_trait)){
                DialogLargeur dialog = new DialogLargeur(MainActivity.this);
                dialog.show();
                //Log.i("Boutton", findViewById(R.id.taille_trait_img)+"");
            }

            if(source == findViewById(R.id.crayon)){
                seleced_Tool = R.id.crayon;
                Log.i("Choix tool", "onClick: crayon: "+R.id.crayon);
            }

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            active = new Point((int) event.getX(), (int) event.getY());

            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.i("Pressed on Canva", "onTouch: Down");

                if(seleced_Tool == R.id.crayon)
                    tempPath = new Path();
                arrive = null;
                depart = null;
                pendant = null;
                depart = new Point((int) event.getX(), (int) event.getY());
            }

            else
                if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.i("Pressed on Canva", "onTouch: Move");
                    pendant = new Point((int)event.getX(),(int)event.getY());
                }
                else
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        Log.i("Pressed on Canva", "onTouch: UP");
                        arrive = new Point( (int)event.getX(), (int)event.getY());

                        if(seleced_Tool == R.id.crayon){
                            dessins.add(new TraceLibre(selected_Color,largeurTrait,isStroke,tempPath));
                        }
                    }

        surf.invalidate();

        return true;
        }
    }

    private class SurfaceDessin extends View{
        Paint pinceau;


        public SurfaceDessin(Context context) {
            super(context);

            this.pinceau = new Paint(Paint.ANTI_ALIAS_FLAG);
            pinceau.setColor(Color.BLACK);
            setBackgroundColor(Color.BLUE);

        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            this.pinceau.setStrokeWidth(largeurTrait);

            if(seleced_Tool == R.id.crayon){
                pinceau.setStyle(Paint.Style.STROKE);
                if(tempPath.isEmpty()){
                    tempPath.moveTo( (float) active.x,(float) active.y);
                }else
                {
                    tempPath.lineTo((float) active.x,(float) active.y);
                }
                canvas.drawPath(tempPath, pinceau);
                Log.i("Pressed", "onDraw: draw Path");
            }

            for (Forme f:dessins
                 ) {
                pinceau.setColor(f.getCouleur());
                if(f.isStroke()){pinceau.setStyle(Paint.Style.STROKE);}
                pinceau.setStrokeWidth(f.getStrokeWidth());

                if(f instanceof TraceLibre)
                    canvas.drawPath(((TraceLibre) f).getChemin(),pinceau);
            }

        }
    }


}