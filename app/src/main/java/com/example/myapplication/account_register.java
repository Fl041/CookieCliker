package com.example.myapplication;

import static com.example.myapplication.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.AccountDBHelper.BASE_VERSION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class account_register<selectedImg> extends AppCompatActivity {

    ImageView avatarImg;
    Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Accueil");

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
                if (!isEmailValid(Email)) {
                    toast("Email");
                } else if (!(isValid(Username) && isValid(Password))) {
                    toast("username ou password");
                } else if (!ImageValid(avatar)) {
                    toast("Avatar");
                } else {
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
            ActivityCompat.requestPermissions(account_register.this, new String[] {
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
        //if (resultCode == RESULT_OK && data != null) {
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

    private String bitmaptoString(Bitmap bit) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byte_Array = stream.toByteArray();
        return Base64.encodeToString(byte_Array, 0);
    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isValid(CharSequence text) {
        return !text.equals("");
    }

    boolean ImageValid(String avatar) {
        return !avatar.equals("");
    }

    void toast(String champs) {
        if (champs.equals("Email")) {
            Toast.makeText(this, "Email invalide", Toast.LENGTH_LONG).show();
        } else if (champs.equals("Avatar")) {
            Toast.makeText(this, "Image invalide", Toast.LENGTH_LONG).show();
        } else {

        }
        Toast.makeText(this, "Le username et le password ne peuvent pas être vide", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), accueil.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}