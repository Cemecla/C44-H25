package com.depression.annexe7;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    SurfaceDessin surf;
    ConstraintLayout main;
    Ecouteur ec;

    Point depart;
    Point arrive;
    Point ligne;

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


        ec = new Ecouteur();
        main = findViewById(R.id.main);

        //1
        surf = new SurfaceDessin(this);
        //2
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1)); // -1 = Match_parent
        //3
        main.addView(surf);

        surf.setOnTouchListener(ec);
    }

    private class SurfaceDessin extends View {

        Paint carre;


        public SurfaceDessin(Context context) {
            super(context);
            setBackgroundResource(R.drawable.carte2);

            this.carre = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.carre.setStrokeCap(Paint.Cap.ROUND);
            this.carre.setStrokeWidth(10);

        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            carre.setColor(Color.RED);




            if(depart != null)
            {
                canvas.drawRect(depart.x-20,depart.y-15,depart.x+20,depart.y+15,carre);
            }
            if(arrive != null){
                canvas.drawRect(arrive.x-20,arrive.y-15,arrive.x+20,arrive.y+15,carre);
            }
            if(ligne != null){
                canvas.drawLine(depart.x,depart.y,ligne.x,ligne.y,carre);
            }
        }
    }

    private class Ecouteur implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                arrive = null;
                depart = null;
                ligne = null;
                depart = new Point( (int)event.getX(), (int)event.getY());
            }
            else
            if(event.getAction() == MotionEvent.ACTION_UP)
                arrive = new Point( (int)event.getX(), (int)event.getY());
            else
            if(event.getAction() == MotionEvent.ACTION_MOVE){
                ligne = new Point((int)event.getX(),(int)event.getY());
            }




            Log.i("event",""+event.getAction());

                surf.invalidate();
            return true; // toujours mettre return true
        }
    }
}