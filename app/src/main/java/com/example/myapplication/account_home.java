package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class account_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String navBar = getString(R.string.navBar);
        getSupportActionBar().setTitle(navBar);

        // Aller au layout login en cliquant sur le bouton "Se Connecter"
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View playBtn) {
                Intent intent = new Intent(getApplicationContext(), account_login.class);
                startActivity(intent);
            }
        });

        // Aller au layout register en cliquant sur le bouton "S'inscrire'"
        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View playBtn) {
                Intent intent = new Intent(getApplicationContext(), account_register.class);
                startActivity(intent);
            }
        });
    }
    // permet de revenir à l'accueil
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()  == android.R.id.home){
            Intent intent = new Intent(getApplicationContext(), accueil.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}