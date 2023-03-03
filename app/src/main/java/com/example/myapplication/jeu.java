package com.example.myapplication;

import static com.example.myapplication.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.AccountDBHelper.BASE_VERSION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

        Button btup = (Button) findViewById(R.id.upgradesBtn) ;

        if(dbHelper.isconnected()){
            Intent ServiceCookie = new Intent(this, com.example.myapplication.ServiceCookie.class);
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

            final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);

            cookie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor cursor = dbHelper.showconnectedaccount() ;
                    cursor.moveToFirst();
                    count = Integer.parseInt(cursor.getString(6));
                    count++ ;
                    dbHelper.updatenbcookies(count);
                    cookieCount.setText("Cookies : " + count);
                }
            });
        }
        else{
            count = 0 ;
            cookieCount.setText("Cookies : " + count);
            // bouton upgrade
            btup.setVisibility(View.INVISIBLE);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()  == android.R.id.home){
            Intent intent = new Intent(getApplicationContext(), accueil.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}