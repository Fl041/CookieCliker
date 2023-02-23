package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class account_show extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_show);

        // Récupérer les informations de l'utilisateur dans la BDD
        String username = "Username : test";
        String email = "E-mail : test@gmail.com";
        String password = "Password : ******";
        String cookie = "Cookie : ";
        String avatar = "Avatar : ";

        // Associer chaque information au TextView correspondant
        TextView usernameShow = findViewById(R.id.usernameShow);
        usernameShow.setText(username + "\n");

        TextView emailShow = findViewById(R.id.emailShow);
        emailShow.setText(email + "\n");

        TextView passwordShow = findViewById(R.id.passwordShow);
        passwordShow.setText(password + "\n");

        TextView cookieShow = findViewById(R.id.cookieShow);
        cookieShow.setText(cookie + "\n");

        TextView avatarShow = findViewById(R.id.avatarShow);
        avatarShow.setText(avatar + "\n");
    }
}