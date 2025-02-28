package com.eeeeeeeeeeeh.annexe6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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

        // 1
        surf = new SurfaceDessin(this);
        // 2
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        // 3

        main.addView(surf);
    }

    public int dpTopx(int dp){
        float density = this.getResources().getDisplayMetrics().density;
        Log.i("densite",""+density);
        Log.i("largeur",getResources().getDisplayMetrics().widthPixels+"");
        return Math.round(dp + density);
    }

    private class SurfaceDessin extends View {
        Paint p;

        public SurfaceDessin(Context context) {
            super(context);
            this.setBackgroundColor(Color.MAGENTA);

            this.p = new Paint(Paint.ANTI_ALIAS_FLAG);// anti-alias => adoucir la courbure

            p.setColor(getResources().getColor(R.color.bleu_clair,null)); // va cherche dans la variable dans le color.xml
            p.setStrokeWidth(2);
        }


        @Override
        protected void onDraw(@NonNull Canvas canvas) {




            super.onDraw(canvas);

            canvas.drawCircle(150,150,80,this.p); // en pixel
            p.setColor(Color.argb(0.32,54,87,244));
            p.setStyle(Paint.Style.STROKE); // Par default, on a le style FILL
            p.setStrokeWidth(20);
            canvas.drawCircle(350,150,80,this.p); // en pixel

            p.setStyle(Paint.Style.FILL);
            p.setColor(Color.BLUE);
            canvas.drawArc(500,500,700,700,0,120,true,this.p);
            p.setColor(Color.RED);
            canvas.drawArc(500,500,700,700,120,120,true,this.p);
            p.setColor(Color.YELLOW);
            canvas.drawArc(500,500,700,700,240,120,true,this.p);




        }


    }

}
