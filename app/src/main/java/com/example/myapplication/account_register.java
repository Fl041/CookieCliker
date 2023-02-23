package com.example.myapplication;

import static com.example.myapplication.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.AccountDBHelper.BASE_VERSION;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class account_register<selectedImg> extends AppCompatActivity {
Bitmap bit ;
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
                String avatar = bitmaptoString(bit);
                Log.d("TAG", String.valueOf(avatar));
                if(!isEmailValid(Email)) {
                    toast("Email");
                }
                else if(!(isValid(Username) && isValid(Password)))
                {
                    toast("username ou password");
                }
                else if(!ImageValid(avatar)){
                    toast("Avatar");
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
            Uri selectedImg = data.getData();
            ImageView imageView = findViewById(R.id.avatarImg);
            imageView.setImageURI(selectedImg);
            bit = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        }
    }
    private String bitmaptoString(Bitmap bit){
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bit.compress(Bitmap.CompressFormat.PNG , 50 , stream) ;
        byte[] byte_Array = stream.toByteArray();
        return Base64.encodeToString(byte_Array , 0);
    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isValid(CharSequence text) {
        return !text.equals("");
    }

    boolean ImageValid(String avatar){
        return !avatar.equals("");
    }
    void toast(String champs) {
        if(champs.equals("Email")) {
            Toast.makeText(this, "Email invalide", Toast.LENGTH_LONG).show();
        }
        else if(champs.equals("Avatar")){
            Toast.makeText(this, "Image invalide", Toast.LENGTH_LONG).show();
        }
        else{

        }
        Toast.makeText(this, "Le username et le password ne peuvent pas être vide", Toast.LENGTH_LONG).show();

    }
}