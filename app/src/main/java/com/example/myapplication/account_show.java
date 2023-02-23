package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        // Click sur le bouton "MODIFIER" affiche la page account_update
        Button accountUpdateBtn = findViewById(R.id.accountUpdateBtn);
        accountUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), account_update.class);
                startActivity(intent);
            }
        });
    }
}