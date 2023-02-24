package com.example.myapplication;

import static com.example.myapplication.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.AccountDBHelper.BASE_VERSION;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class account_update extends AppCompatActivity {
    Bitmap bit ;
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

        username.setHint(cursor.getString(1));
        email.setHint(cursor.getString(2));
        password.setHint(cursor.getString(3));
        imageView.setImageBitmap(stringtobitmap(cursor.getString(4)));

        // Click sur le bouton "ENREGISTRER" qui redirige vers la page de account_show
        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String Username = username.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                bit = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                String avatar = bitmaptoString(bit);
                if(Username.equals("")) Username = cursor.getString(1);
                if(Email.equals("")) Email = cursor.getString(2);
                if(Password.equals("")) Password = cursor.getString(3);
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
                    dbHelper.updateAccount(Username, Email, Password, avatar);
                    Toast.makeText(account_update.this, "Account has been updated.", Toast.LENGTH_SHORT).show();
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

    private Bitmap stringtobitmap(String avatar){
        Bitmap bit = null ;
        try {
            byte[] decodeString = Base64.decode(avatar , Base64.DEFAULT) ;
            bit = BitmapFactory.decodeByteArray(decodeString , 0 , decodeString.length);
        }catch(Exception e){
            Log.d("Exception", "image error");
        }
        return bit ;

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