package com.example.myapplication;

import static com.example.myapplication.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.AccountDBHelper.BASE_VERSION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class account_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Accueil");

        AccountDBHelper dbHelper = new AccountDBHelper(this, BASE_NOM, null, BASE_VERSION);
        EditText getEmail = findViewById(R.id.email);
        EditText getPassword = findViewById(R.id.password);

        // Click sur le bouton "Se connecter" qui redirige vers la page "account_show"
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getEmail.getText().toString();
                String password = getPassword.getText().toString();
                if(dbHelper.existe(email, password )){
                    dbHelper.Disconnect();
                    dbHelper.Connected(email,password);
                    Toast.makeText(account_login.this, "Account has been connected.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), account_show.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(account_login.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        }
        );
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