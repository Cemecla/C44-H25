package com.example.backgammon;

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


        @Override
        public boolean onDrag(View v, DragEvent event) {
            return false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

//            s

            return true;
        }


    }


}