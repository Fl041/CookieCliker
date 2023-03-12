package com.example.myapplication;

import static com.example.myapplication.DataBase.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.DataBase.AccountDBHelper.BASE_VERSION;
import static com.example.myapplication.Fonctions.fonctions.stringtobitmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.database.Cursor;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DataBase.AccountDBHelper;

public class account_show extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_show);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String navBar = getString(R.string.navBar);
        getSupportActionBar().setTitle(navBar);

        // Récupérer les informations de l'utilisateur dans la BDD
        AccountDBHelper dbHelper = new AccountDBHelper(this, BASE_NOM, null, BASE_VERSION);
        Cursor cursor = dbHelper.showconnectedaccount();
        cursor.moveToFirst();
        //String username = "Username : " +cursor.getString(1);
        String username = getString(R.string.username) + " : " + cursor.getString(1);
        String email = getString(R.string.email) + " : " + cursor.getString(2);
        String password = getString(R.string.password) + " : " + cursor.getString(3);
        String cookies = getString(R.string.cookies) + " : " + cursor.getString(6);
        String Imageavatar = cursor.getString(4);

        String avatar = "Avatar : ";

        // Associer chaque information au TextView correspondant
        TextView usernameShow = findViewById(R.id.usernameShow);
        usernameShow.setText(username + "\n");

        TextView emailShow = findViewById(R.id.emailShow);
        emailShow.setText(email + "\n");

        TextView passwordShow = findViewById(R.id.passwordShow);
        passwordShow.setText(password + "\n");

        TextView cookieShow = findViewById(R.id.cookiesnb);
        cookieShow.setText(cookies + "\n");

        TextView avatarShow = findViewById(R.id.avatarShow);
        avatarShow.setText(avatar + "\n");


        ImageView imageAvatar = findViewById(R.id.avatarImg);
        imageAvatar.setImageBitmap(stringtobitmap(Imageavatar));

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