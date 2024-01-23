package com.example.animationapp001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyPathAnimationView pathAnimationView = new MyPathAnimationView(this, R.drawable.baseline_auto_awesome_24);
        LinearLayout layout = findViewById(R.id.main_layout_id);
        layout.addView(pathAnimationView);
        btnStart = findViewById(R.id.button2);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pathAnimationView.startAnimation();
            }
        });
    }
}