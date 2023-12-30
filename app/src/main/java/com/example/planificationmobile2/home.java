package com.example.planificationmobile2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_home);
        ImageView buton_monPalanning=(ImageView) findViewById(R.id.monplaging);
        ImageView buton_nouveau=(ImageView) findViewById(R.id.nouveau);
        ImageView buton_visulaliser=(ImageView) findViewById(R.id.visualisation);
        ImageView buton_analyser=(ImageView) findViewById(R.id.analyse);

        buton_monPalanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nouveau = new Intent(getApplicationContext(),monPlanning.class);
                startActivity(nouveau);
                finish();
            }
        });

        buton_nouveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nouveau = new Intent(getApplicationContext(), com.example.planificationmobile2.nouveau.class);
                startActivity(nouveau);
                finish();
            }
        });

        buton_visulaliser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nouveau = new Intent(getApplicationContext(),visualisation.class);
                startActivity(nouveau);
                finish();
            }
        });

        buton_analyser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nouveau = new Intent(getApplicationContext(),analyser.class);
                startActivity(nouveau);
                finish();
            }
        });

    }
}