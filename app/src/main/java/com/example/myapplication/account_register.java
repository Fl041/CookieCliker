package com.example.myapplication;

import static com.example.myapplication.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.AccountDBHelper.BASE_VERSION;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class account_register<selectedImg> extends AppCompatActivity {
Uri selectedImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);

        AccountDBHelper dbHelper = new AccountDBHelper(this, BASE_NOM, null, BASE_VERSION);

        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        ImageView imageView = findViewById(R.id.avatarImg);



        // Click sur le bouton "S'INSCRIRE" qui redirige vers la page de connexion
        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String Username = username.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                Uri avatar = selectedImg;
                if(!isEmailValid(Email)) {
                    toast("Email");
                }
                if(!(isValid(Username) && isValid(Password)))
                {
                    toast("username ou password");
                }
                else {
                    dbHelper.insertData(Username, Email, Password, avatar);
                    username.setText("");
                    email.setText("");
                    password.setText("");
                    imageView.setImageURI(null);

                    Toast.makeText(account_register.this, "Account has been added.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), account_login.class);
                    startActivity(intent);
                }
            }
        });

        // Click sur le bouton "CHOISIR" pour choisir une image d'avatar
        Button avatarBtn = (Button) findViewById(R.id.avatarBtn);
        avatarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });


    }

    // Afficher l'avatar sélectionné
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null) {
            selectedImg = data.getData();
            Log.d("TAG", String.valueOf(selectedImg));
            ImageView imageView = findViewById(R.id.avatarImg);
            imageView.setImageURI(selectedImg);
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isValid(CharSequence text) {
        return !text.equals("");
    }

    void toast(String champs) {
        if(champs.equals("Email")) {
            Toast.makeText(this, "Email invalide", Toast.LENGTH_LONG).show();
        }
        else{

        }
        Toast.makeText(this, "Le username et le password ne peuvent pas être vide", Toast.LENGTH_LONG).show();

    }
}