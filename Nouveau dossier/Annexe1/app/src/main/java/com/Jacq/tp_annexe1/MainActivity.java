package com.Jacq.tp_annexe1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    SurfaceDessin surf;
    LinearLayout zone_dessin;
    Ecouteur ec_boutton;
    Button validate_button;

    EditText xPos;
    EditText yPos;

    boolean init_dessin;
    Path p;




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

        p = new Path();

        ec_boutton = new Ecouteur();
        zone_dessin = findViewById(R.id.zone_dessin);
        validate_button = findViewById(R.id.validate_button);
        surf = new SurfaceDessin(this);
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1));
        zone_dessin.addView(surf);

        xPos = findViewById(R.id.editVal1);
        yPos = findViewById(R.id.editVal2);


        validate_button.setOnClickListener(ec_boutton);


    }



    private class SurfaceDessin extends View{
        Paint pinceau;

        public SurfaceDessin(Context context) {
            super(context);
            setBackgroundColor(Color.BLUE);
            this.pinceau = new Paint(Paint.ANTI_ALIAS_FLAG);

            this.pinceau.setStrokeWidth(10);


        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            pinceau.setColor(Color.RED);
            pinceau.setStyle(Paint.Style.STROKE);
            pinceau.setStrokeWidth(20);

            if(!((xPos.getText().toString().isEmpty()) || (yPos.getText().toString().isEmpty()) )) {


                float x = Float.parseFloat(xPos.getText().toString());
                float y = Float.parseFloat(yPos.getText().toString());

                if (p.isEmpty()) {
                    p.moveTo(x, y);
                } else
                    p.lineTo(x, y);

                canvas.drawPath(p, pinceau);
            }
        }
    }



    protected class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View source) {



            surf.invalidate();
        }
    }
}