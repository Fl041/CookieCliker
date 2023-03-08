package com.example.myapplication;

import static com.example.myapplication.BasesdeDonnées.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.BasesdeDonnées.AccountDBHelper.BASE_VERSION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.BasesdeDonnées.AccountDBHelper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class jeu extends AppCompatActivity {
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Accueil");
        AccountDBHelper dbHelper = new AccountDBHelper(this, BASE_NOM, null, BASE_VERSION);

        TextView cookieCount = findViewById(R.id.cookieCount);
        ImageView cookie = findViewById(R.id.cookie);
        // Afficher le fragment upgrades après avoir cliqué sur le bouton
        Button upgradesBtn = findViewById(R.id.upgradesBtn);
        FragmentContainerView upgradesFragment = findViewById(R.id.upgradesFragment);
        //affiche le fragment
        upgradesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradesFragment.setVisibility(View.VISIBLE);
            }
        });
        // vérifie si l'utilisateur est connecté
        if(dbHelper.isconnected()){
            //si c'est le cas il ira chercher les données dans la bd
            Intent ServiceCookie = new Intent(this, com.example.myapplication.Service.ServiceCookie.class);
            startService(ServiceCookie);
            final Runnable task = new Runnable() {
                @Override
                public void run() {
                    Cursor cursor = dbHelper.showconnectedaccount();
                    cursor.moveToFirst();
                    count = Integer.parseInt(cursor.getString(6));
                    cookieCount.setText("Cookies : " + count);
                }
            };
            //actualise le nombre de cookies toute les 0.1 seconde
            final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(task, 0, 100, TimeUnit.MILLISECONDS);
            // Incrémenter le nombre de cookies dans la bd
           cookie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor cursor = dbHelper.showconnectedaccount() ;
                    cursor.moveToFirst();
                    count = Integer.parseInt(cursor.getString(6));
                    if(cursor.getString(7).equals("1")){
                        count += 2;
                    }
                    else{
                        count++ ;
                    }
                    dbHelper.updatenbcookies(count);
                }
            });
        }
        else{
            //sinon la valeur de cookie est de 0
            count = 0 ;
            cookieCount.setText("Cookies : " + count);
            // bouton upgrade
            upgradesBtn.setVisibility(View.INVISIBLE);
            // Incrémenter le nombre de cookies
            cookie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count++;
                    cookieCount.setText("Cookies : " + count);
                }
            });
        }

    }
    // permet de revenir à l'accueil
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), accueil.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}