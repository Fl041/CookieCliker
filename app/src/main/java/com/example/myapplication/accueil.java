package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // Aller au layout jeu en cliquant sur le bouton "JOUER"
        Button playBtn = findViewById(R.id.playBtn);
        playBtn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View playBtn) {
                Intent intent = new Intent(getApplicationContext(), jeu.class);
                startActivity(intent);
            }
        });

        // Aller au layout account en cliquant sur le bouton "MON COMPTE"
        Button accountBtn = findViewById(R.id.accountBtn);
        accountBtn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View playBtn) {
                Intent intent = new Intent(getApplicationContext(), account_home.class);
                startActivity(intent);
            }
        });
    }
}