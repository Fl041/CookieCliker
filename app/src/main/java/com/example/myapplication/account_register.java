package com.example.myapplication;

import static com.example.myapplication.DataBase.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.DataBase.AccountDBHelper.BASE_VERSION;
import static com.example.myapplication.Fonctions.fonctions.ImageValid;
import static com.example.myapplication.Fonctions.fonctions.bitmaptoString;
import static com.example.myapplication.Fonctions.fonctions.getCircularBitmap;
import static com.example.myapplication.Fonctions.fonctions.isEmailValid;
import static com.example.myapplication.Fonctions.fonctions.isValid;
import static com.example.myapplication.Fonctions.fonctions.toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.DataBase.AccountDBHelper;


public class account_register extends AppCompatActivity {

    Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String navBar = getString(R.string.navBar);
        getSupportActionBar().setTitle(navBar);

        AccountDBHelper dbHelper = new AccountDBHelper(this, BASE_NOM, null, BASE_VERSION);

        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        ImageView imageView = findViewById(R.id.avatarImg);


        // Click sur le bouton "S'INSCRIRE" qui redirige vers la page de connexion
        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            //insere les données s'ils sont valides dans le bd
            @Override
            public void onClick(View view) {
                String Username = username.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String avatar = bitmaptoString(bit);
                if (!isEmailValid(Email)) {
                    toast("Email" , getApplicationContext());
                } else if (!isValid(Username)) {
                    toast("Username", getApplicationContext());
                } else if (!isValid(Password)) {
                    toast("Password", getApplicationContext());
                } else if (!ImageValid(avatar)) {
                    toast("avatar", getApplicationContext());
                } else {
                    dbHelper.insertData(Username, Email, Password, avatar);
                    username.setText("");
                    email.setText("");
                    password.setText("");
                    imageView.setImageURI(null);

                    String registerToast = getString(R.string.registerToast);
                    Toast.makeText(account_register.this, registerToast, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), account_login.class);
                    startActivity(intent);
                }
            }
        });

        // Click sur le bouton "DOCUMENTS" pour choisir une image d'avatar dans les fichiers du téléphone
        Button avatarBtn = (Button) findViewById(R.id.avatarBtn);
        avatarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });


        // Click sur le bouton "CAMERA" pour prendre une photo
        Button avatarBtnCamera = (Button) findViewById(R.id.avatarBtnCamera);
        // Demande d'autorisation d'exécution de la caméra
        if (ContextCompat.checkSelfPermission(account_register.this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(account_register.this, new String[]{
                    android.Manifest.permission.CAMERA
            }, 100);
        }
        avatarBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, 100);
            }
        });
    }

    // Afficher l'avatar sélectionné
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pour les DOCUMENTS
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 3) {
                Uri selectedImg = data.getData();
                ImageView imageView = findViewById(R.id.avatarImg);
                imageView.setImageURI(selectedImg);
                bit = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                bit = getCircularBitmap(bit);
                imageView.setImageBitmap(bit);
            }

            // Pour la CAMERA
            if (requestCode == 100) {
                bit = (Bitmap) data.getExtras().get("data");
                bit = getCircularBitmap(bit);
                ImageView avatarImgCamera = findViewById(R.id.avatarImg);
                avatarImgCamera.setImageBitmap(bit);
            }
        }

    }
    // permet de revenir à l'accueil
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), accueil.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}