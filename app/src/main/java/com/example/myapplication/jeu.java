package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class jeu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        // Incrémenter le nombre de cookies
        TextView cookieCount = findViewById(R.id.cookieCount);
        ImageView cookie = findViewById(R.id.cookie);
        final int[] count = {0};

        cookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0]++;
                cookieCount.setText("Cookies : " + count[0]);
            }
        });
    }
}