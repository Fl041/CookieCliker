package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class account_show extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_show);

        // Valeurs à récupérer dans la BDD
        String username = "Username : test";
        String email = "E-mail : test@gmail.com";
        String password = "Password : ******";

        // Texte à afficher dans le layout activity_account_show
        TextView text = findViewById(R.id.accountShow);
        text.setText(username + "\n\n" + email + "\n\n" + password);

    }
}