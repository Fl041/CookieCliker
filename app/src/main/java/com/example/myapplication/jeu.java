package com.example.myapplication;

import static com.example.myapplication.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.AccountDBHelper.BASE_VERSION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

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

    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Accueil");
        AccountDBHelper dbHelper = new AccountDBHelper(this, BASE_NOM, null, BASE_VERSION);

        TextView cookieCount = findViewById(R.id.cookieCount);
        ImageView cookie = findViewById(R.id.cookie);

        cookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                cookieCount.setText("Cookies : " + count);
            }
        });

        // Afficher le fragment upgrades après avoir cliqué sur le bouton
        Button upgradesBtn = findViewById(R.id.upgradesBtn);
        FragmentContainerView upgradesFragment = findViewById(R.id.upgradesFragment);

        upgradesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradesFragment.setVisibility(View.VISIBLE);
            }
        });
    }
}