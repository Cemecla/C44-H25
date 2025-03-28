package com.CaronCorp.tp1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.RoundedCorner;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.IllegalFormatCodePointException;
import java.util.Vector;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout zone_dessin;

    int largeurTrait;
    LinearLayout mainView;

    // Permet de mettre le int de l'id des Frames Images
    int selected_Tool;
    String selected_Color;

    boolean isStroke;


    //Variable pour la gestion des dessins
    Point depart;
    Point pendant;
    Point arrive;
    Point active;

    SurfaceDessin surf;

    Vector<Forme> dessins;
    Path tempPath;
    boolean End_triangle;
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
        ColorPickerEvent col_ec = new ColorPickerEvent();
        dessins = new Vector<>();


        // Utiliser le crayon par defaut
        selected_Tool = R.id.crayon;

        largeurTrait = 10;
        zone_dessin = findViewById(R.id.zone_dessin);
        selected_Tool = -1;
        isStroke = true;

        mainView = findViewById(R.id.main);


        surf = new SurfaceDessin(this);
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1));
        surf.setBackgroundColor(Color.WHITE);
        surf.setTag(colorEnHex(Color.WHITE));// Mets le tag de couleur
        surf.setId(View.generateViewId()); // Génere un id unique, je voulais changer les couleurs en jouant avec les ids
        zone_dessin.addView(surf);

        //Variable temporaire pour test
        selected_Color = colorEnHex(Color.BLACK);




        zone_dessin.setOnTouchListener(ec);

        for (int i = 0; i < mainView.getChildCount() ; i++) {

            if(mainView.getChildAt(i) instanceof HorizontalScrollView){
                HorizontalScrollView ScrollFrame =  (HorizontalScrollView) mainView.getChildAt(i);


                    if((ScrollFrame.getChildAt(0) instanceof LinearLayout) && ScrollFrame.getChildAt(0).getId() == R.id.couleurs){

                        LinearLayout tempFrame = (LinearLayout) ScrollFrame.getChildAt(0);
                        for (int j = 0; j < tempFrame.getChildCount(); j++) {
                            tempFrame.getChildAt(j).setOnClickListener(col_ec);
                            }
                    }
                    else
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


    private String colorEnHex(int color) {
        return String.format("#%06X", 0xFFFFFF & color);
    }


    private class ColorPickerEvent implements View.OnClickListener{

        @Override
        public void onClick(View source) {


                selected_Color = source.getTag().toString();
                Log.i("Selected Color", "onClick set: "+selected_Color);


        }
    }



    private class Ecouteur implements View.OnClickListener, View.OnTouchListener {


        @Override
        public void onClick(View source) {

            if (source == findViewById(R.id.taille_trait)) {
                DialogLargeur dialog = new DialogLargeur(MainActivity.this);
                dialog.show();
            }
            else
            if (source == findViewById(R.id.crayon)) {
                selected_Tool = R.id.crayon;
                }
            else
                if(source == findViewById(R.id.efface)){
                    selected_Tool = R.id.efface;
                }
            else
                if (source == findViewById(R.id.rectangle)){
                    selected_Tool = R.id.rectangle;
                }
            else
                if (source == findViewById(R.id.cercle)){
                    selected_Tool = R.id.cercle;
                }
            else
                if(source == findViewById(R.id.triangle)){
                    selected_Tool = R.id.triangle;
                    End_triangle = false;
                }
            else
                if (source == findViewById(R.id.undo)){
                    //function de undo
                }
            else
                if(source == findViewById(R.id.redo)){
                    //function de redo
                }
            else
                if(source == findViewById(R.id.save)){
                    //function de save
                }
            else
                if(source == findViewById(R.id.sceau)){
                    //function fill tool
                    surf.setBackgroundColor(Color.parseColor(selected_Color));
                    surf.setTag((String) selected_Color);
                }
            else
                if(source == findViewById(R.id.palette)){
                    //function de selection de couleur
                }
            else
                if(source == findViewById(R.id.pipette))
                {
                    //function de copie de couleur
                }


            Log.i("Choix tool", "onClick: efface ID: " + R.id.efface);
            Log.i("Choix tool", "onClick: crayon ID: " + R.id.crayon);
            Log.i("Choix tool", "onClick: selected_Tool: " + selected_Tool);

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            active = new Point((int) event.getX(), (int) event.getY());


            /**
             * Action DOWN
             */
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.i("Pressed on Canva", "onTouch: Down");
                tempPath = new Path();
                if(selected_Tool == R.id.triangle && End_triangle){
                    tempPath.moveTo(depart.x,depart.y);
                    tempPath.lineTo(pendant.x,pendant.y);
                    tempPath.lineTo((int) event.getX(), (int) event.getY());
                    tempPath.close();
                }

                arrive = null;
                depart = null;
                pendant = null;
                depart = new Point((int) event.getX(), (int) event.getY());
                if (selected_Tool == R.id.crayon || selected_Tool == R.id.efface) {
                    tempPath.moveTo(event.getX(), event.getY());
                }



            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                Log.i("Pressed on Canva", "onTouch: Move");
                pendant = new Point((int) event.getX(), (int) event.getY());

                if ((selected_Tool == R.id.crayon || selected_Tool == R.id.efface) && tempPath != null) {
                    tempPath.lineTo(event.getX(), event.getY());
                }




            } else if (event.getAction() == MotionEvent.ACTION_UP) {

                Log.i("Pressed on Canva", "onTouch: UP");
                arrive = new Point((int) event.getX(), (int) event.getY());



                if ((selected_Tool == R.id.crayon || selected_Tool == R.id.efface) && tempPath != null) {
                    String couleur;

                    if(selected_Tool == R.id.efface){
                        couleur = "#Background";
                    }
                    else
                        couleur = selected_Color;

                    Log.i("Couleur", "onTouch: "+couleur);

                    Path pathCopy = new Path(tempPath);
                    dessins.add(new TraceLibre(
                            couleur,
                            largeurTrait,
                            isStroke,
                            pathCopy
                    ));
                    tempPath = null;
                }

                else if (selected_Tool == R.id.rectangle) {
                    dessins.add(new Rectangle(selected_Color,largeurTrait,true,depart,arrive));

                }
                else if(selected_Tool == R.id.cercle){
                    int rayon = (int) Math.round(Math.sqrt(Math.pow(pendant.x - depart.x, 2) + Math.pow(pendant.y - depart.y, 2)));
                    Point centre = new Point(depart.x,depart.y);
                    dessins.add(new Oval(selected_Color,largeurTrait,true,rayon,centre));
                }
                else if (selected_Tool == R.id.triangle) {
                    End_triangle = true;


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
            //pinceau.setColor(Color.BLACK);


        }

        // Modifications à apporter dans la classe SurfaceDessin, méthode onDraw()

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            pinceau.reset();




            // Dessiner d'abord tous les traits existants
            for (Forme f : dessins) {


                if(f.getCouleur().equals("#Background"))
                    pinceau.setColor(Color.parseColor(surf.getTag().toString()));
                else
                    pinceau.setColor(Color.parseColor(f.getCouleur()));

                pinceau.setStrokeWidth(f.getStrokeWidth());
                pinceau.setStrokeJoin(Paint.Join.ROUND);

                if (f.isStroke()) {
                    pinceau.setStyle(Paint.Style.STROKE);
                } else {
                    pinceau.setStyle(Paint.Style.FILL);
                }

                if (f instanceof TraceLibre) {
                    canvas.drawPath(((TraceLibre) f).getChemin(), pinceau);
                }
                else if (f instanceof Rectangle){
                    Point tempDepart = ((Rectangle) f).getDepart();
                    Point tempArrive = ((Rectangle) f).getArrive();
                    canvas.drawRect(tempDepart.x,tempDepart.y,tempArrive.x,tempArrive.y,pinceau);
                }
                else if(f instanceof Oval){
                    Point centre = ((Oval) f).getCentre();
                    int rayon = ((Oval) f).getRayon();
                    canvas.drawCircle(centre.x,centre.y,rayon,pinceau);
                }

            }


            //Reset le painceau pour dessiner.
            pinceau.reset();
            pinceau.setColor(Color.parseColor(selected_Color));
            pinceau.setStrokeWidth(largeurTrait);
            pinceau.setStyle(Paint.Style.STROKE);
            // Dessiner le trait temporaire (en cours de dessin)
            if(selected_Tool == R.id.crayon || selected_Tool == R.id.efface){

                if(selected_Tool == R.id.efface)
                    pinceau.setColor(Color.parseColor(surf.getTag().toString()));


                if(tempPath !=null)
                    if (!tempPath.isEmpty())
                        canvas.drawPath(tempPath, pinceau);
            }

            else if (selected_Tool == R.id.rectangle) {


                    if(pendant != null)
                        canvas.drawRect(depart.x,depart.y,pendant.x,pendant.y,pinceau);
                    else
                        canvas.drawRect(depart.x,depart.y,depart.x,depart.y,pinceau);
                }
            else if (selected_Tool == R.id.cercle) {
                    int rayon;
                    if(pendant != null)
                        rayon = (int) Math.round(Math.sqrt(Math.pow(pendant.x - depart.x, 2) + Math.pow(pendant.y - depart.y, 2)));
                    else
                        rayon = 0;
                    canvas.drawCircle(depart.x,depart.y,rayon,pinceau);

            }
            else if(selected_Tool == R.id.triangle){
                if(pendant !=null)
                    canvas.drawLine(depart.x,depart.y,pendant.x,pendant.y,pinceau);
                Log.i("Triangle", "onDraw: line"+ End_triangle);

                if (End_triangle){
                    canvas.drawPath(tempPath,pinceau);
                    Log.i("Triangle", "onDraw: Close triangle");
                }
            }

        }


        }

    }


