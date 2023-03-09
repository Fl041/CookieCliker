package com.example.myapplication.Service;

import static com.example.myapplication.DataBase.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.DataBase.AccountDBHelper.BASE_VERSION;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.myapplication.DataBase.AccountDBHelper;

public class ServiceCookie extends IntentService {
    AccountDBHelper dbHelper = new AccountDBHelper(this, BASE_NOM, null, BASE_VERSION);

    public ServiceCookie() {
        super("Cookie service");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // temps que l'utilisateur est connecté son nombre de cookie augmentera de 1 toute les 5 secondes
        while (dbHelper.isconnected()) {
            synchronized (this) {
                try {
                    wait(5000);
                    Cursor cursor = dbHelper.showconnectedaccount();
                    cursor.moveToFirst();
                    int nbcookie = Integer.parseInt(cursor.getString(6));
                    dbHelper.updatenbcookies(nbcookie + 1);
                } catch (Exception e) {
                }
            }
        }

    }
}