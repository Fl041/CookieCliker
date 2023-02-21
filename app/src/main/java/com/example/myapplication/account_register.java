package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;

public class account_register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);

        // Click sur le bouton "CHOISIR" pour choisir une image d'avatar
        Button avatarBtn = (Button) findViewById(R.id.avatarBtn);
        avatarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        // Click sur le bouton "S'INSCRIRE" qui redirige vers la page de connexion
        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), account_login.class);
                startActivity(intent);
            }
        });
    }

    // Afficher l'avatar sélectionné
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null) {
            Uri selectedImg = data.getData();
            ImageView imageView = findViewById(R.id.avatarImg);
            imageView.setImageURI(selectedImg);
        }
    }
}