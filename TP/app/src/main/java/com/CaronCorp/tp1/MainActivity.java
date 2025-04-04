package com.CaronCorp.tp1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout zoneDessin;
    private LinearLayout mainView;
    private SurfaceDessin surfaceDessin;
    private ChipGroup groupCouleurs;

    private Bitmap bitmapImage;

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

        // Initialisation de ma vue
        zoneDessin = findViewById(R.id.zone_dessin);
        mainView = findViewById(R.id.main);
        groupCouleurs = findViewById(R.id.couleurs);

        // Initialisation de la surface
        surfaceDessin = new SurfaceDessin(this);
        surfaceDessin.setLayoutParams(new ConstraintLayout.LayoutParams(-1, -1));
        zoneDessin.addView(surfaceDessin);


        setupEventListeners();
    }




    private void setupEventListeners() {

        Ecouteur_click ecouteurClick = new Ecouteur_click();

        // Boucle pour setup les écouteurs (Buttons)
        for (int i = 0; i < mainView.getChildCount(); i++) {
            if (mainView.getChildAt(i) instanceof HorizontalScrollView) {
                HorizontalScrollView scrollFrame = (HorizontalScrollView) mainView.getChildAt(i);

                if (scrollFrame.getChildAt(0) instanceof LinearLayout) {
                    LinearLayout tempFrame = (LinearLayout) scrollFrame.getChildAt(0);
                    for (int j = 0; j < tempFrame.getChildCount(); j++) {
                        tempFrame.getChildAt(j).setOnClickListener(ecouteurClick);
                    }
                }
            }
        }

        // Boucle pour setup les écouteurs (Chips)
        ColorPickerListener colorPickerListener = new ColorPickerListener();
        for (int i = 0; i < groupCouleurs.getChildCount(); i++) {
            Chip temp = (Chip) groupCouleurs.getChildAt(i);
            temp.setOnCheckedChangeListener(colorPickerListener);
        }


        zoneDessin.setOnTouchListener(new TouchListener());
    }


    public int getLargeurTrait() {
        return surfaceDessin.largeurTrait;
    }


    public void setLargeurTrait(int largeurTrait) {
        surfaceDessin.largeurTrait = largeurTrait;
    }




    private class Ecouteur_click implements View.OnClickListener {
        @Override
        public void onClick(View source) {
            int sourceId = source.getId();

            if (sourceId == R.id.taille_trait) {

                DialogLargeur dialog = new DialogLargeur(MainActivity.this);
                dialog.show();

            } else if (sourceId == R.id.sceau) {

                surfaceDessin.setBackgroundColor(Color.parseColor(surfaceDessin.couleurSelectionnee));
                surfaceDessin.setTag(surfaceDessin.couleurSelectionnee);
                surfaceDessin.invalidate();

            }
            else {

                if (surfaceDessin.outilCourant == R.id.triangle && sourceId != R.id.triangle)
                    surfaceDessin.trianglePoints.clear();

                surfaceDessin.outilCourant = sourceId;
            }
        }
    }

    private class ColorPickerListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                String couleur = buttonView.getTag().toString();
                surfaceDessin.couleurSelectionnee = couleur;
            }
        }
    }


    private class TouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    surfaceDessin.ActionDown(x, y);
                    break;

                case MotionEvent.ACTION_MOVE:
                    surfaceDessin.ActionMove(x, y);
                    break;

                case MotionEvent.ACTION_UP:
                    surfaceDessin.ActionUp(x, y);
                    break;
            }

            return true;
        }
    }


    private class SurfaceDessin extends View {
        private Paint pinceau;
        private Vector<Forme> dessins;

        private Point depart;
        private Point pendant;
        private Point arrive;
        private Path tempPath;

        // Liste de epoints pour le triangle
        private ArrayList<Point> trianglePoints = new ArrayList<>();


        private int outilCourant;
        private String couleurSelectionnee;
        private int largeurTrait;

        public SurfaceDessin(Context context) {
            super(context);
            this.pinceau = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.dessins = new Vector<>();
            outilCourant = R.id.crayon;
            couleurSelectionnee = "#000000";
            largeurTrait = 10;
            setBackgroundColor(Color.WHITE);
            setTag("#FFFFFF"); //Mettre le tag du background
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);


            for (Forme forme : dessins) {
                forme.dessiner(canvas, pinceau, (String) getTag());
            }


            pinceau.reset();
            pinceau.setColor(Color.parseColor(couleurSelectionnee));
            pinceau.setStrokeWidth(largeurTrait);
            pinceau.setStyle(Paint.Style.STROKE);
            pinceau.setStrokeJoin(Paint.Join.ROUND);
            pinceau.setStrokeCap(Paint.Cap.ROUND);


            if (outilCourant == R.id.efface) {
                pinceau.setColor(Color.parseColor((String) getTag()));
            }


            if (outilCourant == R.id.crayon || outilCourant == R.id.efface) {
                //Débug: ne pas déssiner un path vide
                if (tempPath != null && !tempPath.isEmpty()) {
                    canvas.drawPath(tempPath, pinceau);
                }
            } else if (outilCourant == R.id.rectangle && depart != null && pendant != null) {
                canvas.drawRect(depart.x, depart.y, pendant.x, pendant.y, pinceau);
            } else if (outilCourant == R.id.cercle && depart != null && pendant != null) {
                int rayon = (int) Math.round(Math.sqrt(Math.pow(pendant.x - depart.x, 2) + Math.pow(pendant.y - depart.y, 2)));
                canvas.drawCircle(depart.x, depart.y, rayon, pinceau);
            } else if (outilCourant == R.id.triangle) {

                //Gestion du triangle
                int numPoints = trianglePoints.size();
                if (numPoints > 0) {

                    //Premier click
                    for (int i = 0; i < numPoints - 1; i++) {
                        Point p1 = trianglePoints.get(i);
                        Point p2 = trianglePoints.get(i + 1);
                        canvas.drawLine(p1.x, p1.y, p2.x, p2.y, pinceau);

                    }

                    // Deuxieme click
                    if (numPoints == 2 && pendant != null) {
                        Point p1 = trianglePoints.get(1);
                        canvas.drawLine(p1.x, p1.y, pendant.x, pendant.y, pinceau);
                        canvas.drawLine(pendant.x, pendant.y, trianglePoints.get(0).x, trianglePoints.get(0).y, pinceau);
                    }

                    // Troisième click et plus
                    if (numPoints == 3) {
                        Point p1 = trianglePoints.get(2);
                        Point p2 = trianglePoints.get(0);
                        canvas.drawLine(p1.x, p1.y, p2.x, p2.y, pinceau);
                    }
                }
            }
        }


        public void ActionDown(float x, float y) {

            if (outilCourant == R.id.triangle) {
                pendant = new Point((int) x, (int) y);

            }
            else {

                tempPath = new Path();
                arrive = null;
                depart = new Point((int) x, (int) y);
                pendant = null;

                if (outilCourant == R.id.crayon || outilCourant == R.id.efface) {
                    tempPath.moveTo(x, y);
                }
            }

            invalidate();
        }


        public void ActionMove(float x, float y) {

            pendant = new Point((int) x, (int) y);

            if ((outilCourant == R.id.crayon || outilCourant == R.id.efface) && tempPath != null) {
                tempPath.lineTo(x, y);
            }

            invalidate();
        }


        public void ActionUp(float x, float y) {
            if (outilCourant == R.id.triangle) {

                Point clickPoint = new Point((int) x, (int) y);
                trianglePoints.add(clickPoint);

                //Dessin du path quand tout les points sont séléctionné
                if (trianglePoints.size() == 3) {
                    Path trianglePath = new Path();
                    trianglePath.moveTo(trianglePoints.get(0).x, trianglePoints.get(0).y);
                    trianglePath.lineTo(trianglePoints.get(1).x, trianglePoints.get(1).y);
                    trianglePath.lineTo(trianglePoints.get(2).x, trianglePoints.get(2).y);
                    trianglePath.close();
                    //
                    Triangle triangle = new Triangle(couleurSelectionnee, largeurTrait, trianglePath);
                    dessins.add(triangle);
                    trianglePoints.clear();
                    Log.i("Triangle", "Triangle ajouté");
                }

                invalidate();

            }


            arrive = new Point((int) x, (int) y);
            Forme nouvelleForme = null;

            if ((outilCourant == R.id.crayon || outilCourant == R.id.efface) && tempPath != null) {
                String couleur;
                //Si l'outil est une efface la couleur doit être celle du background
                if(outilCourant == R.id.efface)
                     couleur = "#Background";
                else
                    couleur = couleurSelectionnee;

                Path pathCopy = new Path(tempPath);
                nouvelleForme = new TraceLibre(couleur, largeurTrait, pathCopy);
                tempPath = null;
            } else if (outilCourant == R.id.rectangle) {

                nouvelleForme = new Rectangle(couleurSelectionnee, largeurTrait, depart, arrive);

            } else if (outilCourant == R.id.cercle) {

                int rayon = (int) Math.round(Math.sqrt(Math.pow(pendant.x - depart.x, 2) + Math.pow(pendant.y - depart.y, 2)));
                nouvelleForme = new Oval(couleurSelectionnee, largeurTrait, rayon, depart);

            }else if (outilCourant == R.id.pipette) {
                Bitmap pipette = Bitmap.createBitmap(surfaceDessin.getWidth(), surfaceDessin.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas tempCanvas = new Canvas(pipette);
                surfaceDessin.draw(tempCanvas);
                if (x >= 0 && x < pipette.getWidth() && y >= 0 && y < pipette.getHeight()) {
                    // Récupère la couleur du pixel
                    int pixel = pipette.getPixel((int) x, (int) y);
                    couleurSelectionnee = String.format("#%06X", (0xFFFFFF & pixel));
                    outilCourant = R.id.crayon;
                    for (int i = 0; i < groupCouleurs.getChildCount(); i++) {
                        if(groupCouleurs.getChildAt(i).getTag().toString().matches(couleurSelectionnee))
                            ((Chip)groupCouleurs.getChildAt(i)).setChecked(true);
                    }
                }

            }

            //ajout de la forme dans le vecteur
            if (nouvelleForme != null) {
                dessins.add(nouvelleForme);
            }

            invalidate();
        }



    }
}