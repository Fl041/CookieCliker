package com.example.myapplication;

import static com.example.myapplication.DataBase.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.DataBase.AccountDBHelper.BASE_VERSION;
import static com.example.myapplication.Fonctions.fonctions.ImageValid;
import static com.example.myapplication.Fonctions.fonctions.bitmaptoString;
import static com.example.myapplication.Fonctions.fonctions.getCircularBitmap;
import static com.example.myapplication.Fonctions.fonctions.isEmailValid;
import static com.example.myapplication.Fonctions.fonctions.isValid;
import static com.example.myapplication.Fonctions.fonctions.stringtobitmap;
import static com.example.myapplication.Fonctions.fonctions.toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.DataBase.AccountDBHelper;

public class account_update extends AppCompatActivity {
    Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_update);

        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        ImageView imageView = findViewById(R.id.avatarImg);

        AccountDBHelper dbHelper = new AccountDBHelper(this, BASE_NOM, null, BASE_VERSION);
        // Récupérer les informations de l'utilisateur dans la BDD
        Cursor cursor = dbHelper.showconnectedaccount();
        cursor.moveToFirst();

        username.setText(cursor.getString(1));
        email.setText(cursor.getString(2));
        password.setText(cursor.getString(3));
        imageView.setImageBitmap(stringtobitmap(cursor.getString(4)));

        // Click sur le bouton "ENREGISTRER" qui redirige vers la page de account_show
        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
        //renvoie dans la bd les infos (changer ou non)
            @Override
            public void onClick(View view) {
                String Username = username.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                bit = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                String avatar = bitmaptoString(bit);

                if (!isEmailValid(Email)) {
                    toast("Email" , getApplicationContext());
                } else if (!isValid(Username)) {
                    toast("Username" ,  getApplicationContext());
                } else if (!isValid(Password)) {
                    toast("Password" , getApplicationContext());
                } else if (!ImageValid(avatar)) {
                    toast("avatar", getApplicationContext());
                } else {
                    dbHelper.updateAccount(Username, Email, Password, avatar);
                    String updateToast = getString(R.string.updateToast);
                    Toast.makeText(account_update.this, updateToast, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), account_show.class);
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
        Button avatarBtnCamera = (Button) findViewById(R.id.avatarBtnCamera);
        // Demande d'autorisation d'exécution de la caméra
        if (ContextCompat.checkSelfPermission(account_update.this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(account_update.this, new String[] {
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
}