package com.example.myapplication.Fonctions;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;

public class fonctions {

    //transforme un strind en bitmap
    public static Bitmap stringtobitmap(String avatar) {
        Bitmap bit = null;
        try {
            byte[] decodeString = Base64.decode(avatar, Base64.DEFAULT);
            bit = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        } catch (Exception e) {
            Log.d("Exception", "image error");
        }
        return bit;

    }
    //transforme la bitmap de l'avatar en string
    public static String bitmaptoString(Bitmap bit) {
        if(bit == null ) return "" ;
        else {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byte_Array = stream.toByteArray();
            return Base64.encodeToString(byte_Array, 0);
        }
    }
    //vérifie si l'email est valide
    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    //vérifie si le text n'est pas vide
    public static boolean isValid(CharSequence text) {
        return !text.equals("");
    }

    // vérifie si l'image n'est pas vide
  public  static boolean ImageValid(String avatar) {
        return !avatar.equals("");
    }

    // envoie un message d'erreur selon l'erreur détectée
   public static  void toast(String champs , Context context) {

        if (champs.equals("Username")) {
            String usernameToast = context.getString(R.string.usernameToast);
            Toast.makeText(context, usernameToast, Toast.LENGTH_LONG).show();

        } else if (champs.equals("Email")) {
            String emailToast = context.getString(R.string.emailToast);
            Toast.makeText(context, emailToast, Toast.LENGTH_LONG).show();

        } else if (champs.equals("Password")){
            String passwordToast = context.getString(R.string.passwordToast);
            Toast.makeText(context, passwordToast, Toast.LENGTH_LONG).show();

        } else {
            String avatarToast = context.getString(R.string.avatarToast);
            Toast.makeText(context, avatarToast, Toast.LENGTH_LONG).show();

        }

    }

    // arondie l'avatar
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
