package com.example.myapplication;

import static com.example.myapplication.BasesdeDonnées.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.BasesdeDonnées.AccountDBHelper.BASE_VERSION;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.BasesdeDonnées.AccountDBHelper;

public class accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        AccountDBHelper dbHelper = new AccountDBHelper(this, BASE_NOM, null, BASE_VERSION);
        Button playBtn = findViewById(R.id.playBtn);
        Button accountBtn = findViewById(R.id.accountBtn);
        Button decoBtn = findViewById(R.id.decoBtn);
        //gère le visibilité du bouton déconnecté
        Visibilitybouton(decoBtn);
        //gère l'action réalisé pour le bouton mon compte
        Setfonction(accountBtn);
        // Aller au layout jeu en cliquant sur le bouton "JOUER"
        playBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View playBtn) {
                Intent intent = new Intent(getApplicationContext(), jeu.class);
                startActivity(intent);
            }
        });

        decoBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbHelper.Disconnect();
                        Intent intent = new Intent(getApplicationContext(), accueil.class);
                        startActivity(intent);
                    }
                }
        );

    }
        // si l'utilisateur le bouton sera visible pour sa décoonecté sinon il sera invisible
        public void Visibilitybouton(Button button){
            AccountDBHelper dbHelper = new AccountDBHelper(this, BASE_NOM, null, BASE_VERSION);
            boolean isconnected = dbHelper.isconnected();

            if(!isconnected){
                button.setVisibility(View.INVISIBLE);
            }
            else {
                button.setVisibility(View.VISIBLE);
            }
        }
        //si l'utilisateur est connecté il pour avoir accès aux info de son compte sinon il aura le choix de se connecter ou de s'inscrire
        public void Setfonction(Button button){
            AccountDBHelper dbHelper = new AccountDBHelper(this, BASE_NOM, null, BASE_VERSION);
            boolean isconnected = dbHelper.isconnected();
            if(!isconnected){
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View playBtn) {
                        Intent intent = new Intent(getApplicationContext(), account_home.class);
                        startActivity(intent);
                    }
                });
            }
            else{
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View playBtn) {
                        Intent intent = new Intent(getApplicationContext(), account_show.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }
